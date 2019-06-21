package hr.ferit.brunozoric.taskie.domain.addTask

import hr.ferit.brunozoric.taskie.common.ErrorLambda
import hr.ferit.brunozoric.taskie.common.SuccessLambda
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.repository.AddTaskRepository
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import retrofit2.Callback
import retrofit2.Response

class AddTaskUseCaseImpl(private val saveTaskRepository: AddTaskRepository): AddTaskUseCase {
    override fun execute(body: AddTaskRequest, onSuccess: SuccessLambda<BackendTask>, onFailure: ErrorLambda) {
        saveTaskRepository.addTask(body).enqueue(object: Callback<BackendTask> {
            override fun onFailure(call: retrofit2.Call<BackendTask>, t: Throwable) {
                onFailure(t)
            }

            override fun onResponse(call: retrofit2.Call<BackendTask>, response: Response<BackendTask>) {
                if (response.isSuccessful) response.body()?.run(onSuccess)

                response.errorBody()?.run { onFailure(IllegalStateException("Failure")) }
            }
        })
    }
}