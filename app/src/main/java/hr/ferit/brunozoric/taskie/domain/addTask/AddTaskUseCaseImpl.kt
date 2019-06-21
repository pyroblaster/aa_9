package hr.ferit.brunozoric.taskie.domain.addTask

import hr.ferit.brunozoric.taskie.common.ErrorLambda
import hr.ferit.brunozoric.taskie.common.SuccessLambda
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor

class AddTaskUseCaseImpl (private val taskieInteractor: TaskieInteractor): AddTaskUseCase
{
    override fun execute(task: AddTaskRequest, onTaskAddedSuccess: SuccessLambda<BackendTask>, onFailure: ErrorLambda) {
        //taskieInteractor.save(AddTaskRequest(title, description, priority.getValue()), addTaskCallback())
    }

}