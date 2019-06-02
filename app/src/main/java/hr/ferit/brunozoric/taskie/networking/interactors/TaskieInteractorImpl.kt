package hr.ferit.brunozoric.taskie.networking.interactors

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.model.request.EditTaskRequest
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.DeleteTaskResponse
import hr.ferit.brunozoric.taskie.model.response.GetTasksResponse
import hr.ferit.brunozoric.taskie.model.response.LoginResponse
import hr.ferit.brunozoric.taskie.model.response.RegisterResponse
import hr.ferit.brunozoric.taskie.networking.TaskieApiService
import retrofit2.Callback

class TaskieInteractorImpl(private val apiService: TaskieApiService) : TaskieInteractor {
    override fun getTask(id: String, getTaskCallback: Callback<BackendTask>) {
        apiService.getTask(id).enqueue(getTaskCallback)
    }

    override fun deleteTask(taskId: String, getDeleteTaskCallback: Callback<DeleteTaskResponse>) {
        apiService.deleteTask(taskId).enqueue(getDeleteTaskCallback)
    }

    override fun editTask(request: EditTaskRequest, editTaskCallback: Callback<BackendTask>) {
        apiService.editTask(request).enqueue(editTaskCallback)
    }

    override fun getTasks(taskieResponseCallback: Callback<GetTasksResponse>) {
        apiService.getTasks().enqueue(taskieResponseCallback)
    }

    override fun register(request: UserDataRequest, registerCallback: Callback<RegisterResponse>) {
        apiService.register(request).enqueue(registerCallback)
    }

    override fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>) {
        apiService.login(request).enqueue(loginCallback)
    }

    override fun save(request: AddTaskRequest, saveCallback: Callback<BackendTask>) {
        apiService.save(request).enqueue(saveCallback)
    }
}