package com.paulo.pdm.todo.model

class Task (
    val id: Int? = 0,
    var is_urgent: Boolean,
    val description: String,
    var is_done: Boolean = false
)
