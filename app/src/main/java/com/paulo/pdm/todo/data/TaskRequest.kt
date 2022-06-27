package com.paulo.pdm.todo.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.paulo.pdm.todo.model.Task
import org.json.JSONArray

class TaskRequest(context: Context) {
    private val queue = Volley.newRequestQueue(context)
    companion object {
        private val URL = "http://10.0.0.188"
        private val GET_TASKS = "/task"
    }
    fun startTasksRequest() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                moviesRequest()
                handler.postDelayed(this, 5000)
            }
        })
    }
    private fun moviesRequest() {
        val jsonRequest = JsonArrayRequest(
            Request.Method.GET,
            URL + GET_TASKS,
            null,
            { response ->
                val movieList = JSONArrayToTaskList(response)
                TasksSingleton.updateTaskList(movieList)
            },
            { error ->
                Log.e("TASKREQERR", "task request error: $error")
            }
        )
        this.queue.add(jsonRequest)
    }
    fun JSONArrayToTaskList(jsonArray: JSONArray): ArrayList<Task> {
        val movieList = ArrayList<Task>()
        for(i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val task = Task(
                jsonObject.getBoolean("isUrgent"),
                jsonObject.getString("description"),
                jsonObject.getBoolean("isDone")
            )
            movieList.add(task)
        }
        return movieList
    }
}