package hr.ferit.brunozoric.taskie.model.request

data class EditTaskRequest(val id: String, val title: String, val content: String, val taskPriority: Int)
