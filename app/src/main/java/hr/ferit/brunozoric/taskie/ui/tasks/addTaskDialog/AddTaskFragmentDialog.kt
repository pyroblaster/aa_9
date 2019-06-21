package hr.ferit.brunozoric.taskie.ui.tasks.addTaskDialog

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.RESPONSE_OK
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.common.priorityFactory
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.PriorityColor
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskFragmentDialog : DialogFragment(), AddTaskDialogContract.View {

    private var taskAddedListener: TaskAddedListener? = null
    private val interactor = BackendFactory.getTaskieInteractor()

    companion object {
        fun newInstance(): AddTaskFragmentDialog {
            return AddTaskFragmentDialog()
        }
    }

    interface TaskAddedListener {
        fun onTaskAdded(task: BackendTask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    fun setTaskAddedListener(listener: TaskAddedListener) {
        taskAddedListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_new_task, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi() {
        context?.let {
            prioritySelector.adapter =
                ArrayAdapter<PriorityColor>(it, android.R.layout.simple_spinner_dropdown_item, PriorityColor.values())
            prioritySelector.setSelection(0)
        }
    }

    private fun initListeners() = saveTaskAction.setOnClickListener { saveTask() }

    private fun saveTask() {
        if (isInputEmpty()) {
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val priority = prioritySelector.priorityFactory()
        interactor.save(AddTaskRequest(title, description, priority.getValue()), addTaskCallback())
        clearUi()
    }

    private fun addTaskCallback(): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>?, t: Throwable?) {
            //TODO : handle default error
        }

        override fun onResponse(call: Call<BackendTask>?, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse(response.body())
                    else -> handleSomethingWentWrong()
                }
            }
        }
    }

    private fun clearUi() {
        newTaskTitleInput.text.clear()
        newTaskDescriptionInput.text.clear()
        prioritySelector.setSelection(0)
    }

    private fun isInputEmpty(): Boolean = isEmpty(newTaskTitleInput.text) || isEmpty(newTaskDescriptionInput.text)

    private fun handleOkResponse(task: BackendTask?) = task?.run { onTaskiesReceived(this) }

    private fun handleSomethingWentWrong() = this.activity?.displayToast("Something went wrong!")

    private fun onTaskiesReceived(task: BackendTask) {
        taskAddedListener?.onTaskAdded(task)
        dismiss()
    }

    override fun onTaskAddedSuccess(task: BackendTask) {
        taskAddedListener?.onTaskAdded(task)
        context?.displayToast("Task added.")
        dismiss()
    }

    override fun onTaskAddedFailure() {
        context?.displayToast("Failure.")
    }


}