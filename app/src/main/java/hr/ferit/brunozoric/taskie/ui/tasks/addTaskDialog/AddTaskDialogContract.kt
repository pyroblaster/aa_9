package hr.ferit.brunozoric.taskie.ui.tasks.addTaskDialog

import hr.ferit.brunozoric.taskie.model.BackendTask

interface AddTaskDialogContract {
    interface View {
        fun onTaskAddedSuccess(task: BackendTask)
        fun onTaskAddedFailure(messag:String)
    }

    interface Presenter {
        fun setView(view: View)
        fun onTaskAdded(title: String, content: String, taskPriority: Int)
    }
}