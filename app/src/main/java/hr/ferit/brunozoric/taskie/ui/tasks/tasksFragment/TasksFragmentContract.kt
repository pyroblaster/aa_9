package hr.ferit.brunozoric.taskie.ui.tasks.tasksFragment

interface TasksFragmentContract {
    interface View{

    }
    interface Presenter{
        fun setView(view: View)
    }
}