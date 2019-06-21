package hr.ferit.brunozoric.taskie.ui.tasks

import android.os.Bundle
import android.text.Editable
import android.view.View
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.common.EXTRA_TASK_ID
import hr.ferit.brunozoric.taskie.common.RESPONSE_OK
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.PriorityColor
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskDetailsFragment : BaseFragment(),
    UpdateTaskFragmentDialog.EditTaskLIstener {

    private var taskID = NO_TASK
    private val taskieInteractor = BackendFactory.getTaskieInteractor()

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_task_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        initListeners()
    }

    override fun onTaskEdited(backendTask: BackendTask) {
        tryDisplayTask(taskID)
    }

    private fun initListeners() {
        updateTaskButton.setOnClickListener { editTask(taskID) }
    }

    private fun editTask(taskID: String) {
        val dialog = UpdateTaskFragmentDialog.newInstance()
        dialog.taskId = taskID
        dialog.setEditTaskLIstener(this)
        dialog.show(childFragmentManager,dialog.tag)
    }

    private fun tryDisplayTask(taskID: String) {
        try {
            taskieInteractor.getTask(taskID, getTaskCallback())

        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun displayTask(task: Task) {
        detailsTaskTitle.text = task.title
        detailsTaskDescription.text = task.description
        detailsPriorityView.setBackgroundResource(task.priority.getColor())
    }

    private fun getTaskCallback(): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {
        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse(response)
                    else -> handleSomethingWentWrong()
                }
            }
        }
    }
    private fun handleSomethingWentWrong() = Taskie.instance.displayToast("Something went wrong!")

    private fun handleOkResponse(response: Response<BackendTask>) {

        response.body()?.run {
            detailsTaskTitle.text= Editable.Factory.getInstance().newEditable(title)
            detailsTaskDescription.text=Editable.Factory.getInstance().newEditable(content)
            detailsPriorityView.setBackgroundResource(when(taskPriority){
                1 -> PriorityColor.LOW.getColor()
                2-> PriorityColor.MEDIUM.getColor()
                else -> PriorityColor.HIGH.getColor()
            })
        }

    }
    companion object {
        const val NO_TASK = "empty"

        fun newInstance(taskId: String): TaskDetailsFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
