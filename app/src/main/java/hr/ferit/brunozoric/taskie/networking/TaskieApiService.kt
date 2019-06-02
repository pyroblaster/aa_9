package hr.ferit.brunozoric.taskie.networking

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.model.request.EditTaskRequest
import hr.ferit.brunozoric.taskie.model.request.UserDataRequest
import hr.ferit.brunozoric.taskie.model.response.DeleteTaskResponse
import hr.ferit.brunozoric.taskie.model.response.GetTasksResponse
import hr.ferit.brunozoric.taskie.model.response.LoginResponse
import hr.ferit.brunozoric.taskie.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*


interface TaskieApiService {

    @POST("/api/register")
    fun register(@Body userData: UserDataRequest): Call<RegisterResponse>

    @POST("/api/login")
    fun login(@Body userData: UserDataRequest): Call<LoginResponse>

    @GET("/api/note")
    fun getTasks(): Call<GetTasksResponse>

    @POST("/api/note")
    fun save(@Body taskData: AddTaskRequest): Call<BackendTask>

    @POST("/api/note/delete")
    fun deleteTask(@Query("id")id:String): Call<DeleteTaskResponse>

    @POST("/api/note/edit")
    fun editTask(@Body editTaskRequest: EditTaskRequest): Call <BackendTask>

    @GET("/api/note/{taskID}")
    fun getTask(@Path("taskID") id:String): Call<BackendTask>
}