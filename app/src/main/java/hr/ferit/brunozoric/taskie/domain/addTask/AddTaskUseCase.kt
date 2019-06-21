package hr.ferit.brunozoric.taskie.domain.addTask

import hr.ferit.brunozoric.taskie.common.ErrorLambda
import hr.ferit.brunozoric.taskie.common.SuccessLambda
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest

interface AddTaskUseCase {
    fun execute(task:AddTaskRequest, onTaskAddedSuccess:SuccessLambda<BackendTask>, onFailure:ErrorLambda)
}