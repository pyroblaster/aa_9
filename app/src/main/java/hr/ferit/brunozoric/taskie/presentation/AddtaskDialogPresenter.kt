package hr.ferit.brunozoric.taskie.presentation

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.AddTaskRequest
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import hr.ferit.brunozoric.taskie.ui.tasks.addTaskDialog.AddTaskDialogContract

class AddtaskDialogPresenter:AddTaskDialogContract.Presenter{
    override fun onTaskAdded() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var view: AddTaskDialogContract.View

    override fun setView(view: AddTaskDialogContract.View) {
        this.view = view
    }


}