package hr.ferit.brunozoric.taskie.model.response

data class GetTaskResponse(val id: String, val title: String, val content: String, val taskPriority: Int)