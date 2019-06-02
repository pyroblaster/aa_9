package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.common.RESPONSE_OK
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.PriorityColor

import hr.ferit.brunozoric.taskie.model.request.EditTaskRequest
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import kotlinx.android.synthetic.main.fragment_task_details.*
import kotlinx.android.synthetic.main.fragment_update_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateTaskFragmentDialog:DialogFragment(){

    private val taskieInteractor = BackendFactory.getTaskieInteractor()
    private var editTaskListener: EditTaskLIstener? = null
    lateinit var task: BackendTask
    var taskId:String = ""

    companion object {
        fun newInstance(): UpdateTaskFragmentDialog {
            return UpdateTaskFragmentDialog()
        }
    }

    interface EditTaskLIstener {
        fun onTaskEdited(backendTask: BackendTask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    private fun onTaskEdited(backendTask: BackendTask){
        editTaskListener?.onTaskEdited(backendTask)
    }


    fun setEditTaskLIstener(listener: EditTaskLIstener){
        editTaskListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update_task, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskieInteractor.getTask(taskId, getTaskCallback())
        initUi()
        initListeners()
    }

    private fun initUi(){
        context?.let {
            taskPriorityUpdate.adapter = ArrayAdapter<PriorityColor>(it, android.R.layout.simple_spinner_dropdown_item, PriorityColor.values())
            taskPriorityUpdate.setSelection(0)
        }
    }

    private fun initListeners(){
        saveTaskUpdate.setOnClickListener{ editTask() }
    }

    private fun editTask() {
        if (isInputEmpty()){
            context?.displayToast(getString(R.string.emptyFields))
        }
        else{
        val title = tasktTitleUpdate.text.toString()
        val description = taskDescUpdate.text.toString()
        val priority = taskPriorityUpdate.selectedItemPosition
        taskieInteractor.editTask(EditTaskRequest(taskId, title, description, priority),editTaskCallback())
        }
    }




    private fun clearUi() {
        tasktTitleUpdate.text.clear()
        taskDescUpdate.text.clear()
        taskPriorityUpdate.setSelection(0)
    }


    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(tasktTitleUpdate.text) || TextUtils.isEmpty(
        taskDescUpdate.text
    )

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

    private fun editTaskCallback() = object:  Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {
        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> updateOkResponse(response)
                    else -> updateFailedResponse()
                }
            }
        }

    }
    private fun updateFailedResponse() {

    }

    private fun updateOkResponse(response: Response<BackendTask>) {
        response.body()?.run {
            onTaskEdited(this)
        }
    }



    private fun handleSomethingWentWrong() = Taskie.instance.displayToast("Something went wrong!")

    private fun handleOkResponse(response: Response<BackendTask>) {

        response.body()?.run {

        }

    }

}