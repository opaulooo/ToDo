package com.paulo.pdm.todo.data

import androidx.compose.runtime.mutableStateListOf
import com.paulo.pdm.todo.model.Task

object TasksSingleton {
    private val task = mutableStateListOf<Task>()
    fun updateTaskList(task: ArrayList<Task>) {
        this.task.clear()
        this.task.addAll(task)
    }
    fun getTasks(): List<Task> {
        return this.task
    }
}