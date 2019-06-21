package hr.ferit.brunozoric.taskie.model.repository

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import retrofit2.Call

interface AddTaskRepository {
    fun addTask(body:AddTaskRequest): Call<BackendTask>
}