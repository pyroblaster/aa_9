package hr.ferit.brunozoric.taskie.presentation

import android.service.autofill.SaveCallback
import hr.ferit.brunozoric.taskie.domain.addTask.AddTaskUseCase
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.model.response.GetTasksResponse
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import hr.ferit.brunozoric.taskie.ui.tasks.addTaskDialog.AddTaskDialogContract
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddtaskDialogPresenter(private val addTaskUseCase: AddTaskUseCase):AddTaskDialogContract.Presenter{
    private lateinit var view: AddTaskDialogContract.View

    override fun setView(view: AddTaskDialogContract.View) {
        this.view = view
    }

    override fun onTaskAdded(title: String, content: String, taskPriority: Int) {
        addTaskUseCase.execute(AddTaskRequest(title, content,taskPriority),::onTaskAddedSuccess,::onTaskAddedFailure)
    }

    private fun onTaskAddedSuccess(task:BackendTask){
        view.onTaskAddedSuccess(task)
    }

    private fun onTaskAddedFailure(throwable: Throwable){
        view.onTaskAddedFailure(throwable.localizedMessage)
    }

}