package com.paulo.pdm.todo.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.paulo.pdm.todo.model.Task
import org.json.JSONArray
import org.json.JSONObject

class TaskRequest(context: Context) {
    private val queue = Volley.newRequestQueue(context)

    companion object {
        private val URL = "http://10.0.2.2:8000"
        private val GET = "/tasks"
        private val POST = "/tasks/new"
        private val DELETE = "/tasks/del"
        private val UPDATE = "/tasks/done"
    }

    fun startTasksRequest() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                tasksRequest()
                handler.postDelayed(this, 5000)
            }
        })
    }

    private fun tasksRequest() {
        val jsonRequest = JsonArrayRequest(
            Request.Method.GET, URL + GET, null,
            { response ->
                val task = JSONArrayToTaskList(response)
                TasksSingleton.updateTaskList(task)
            },
            { error ->
                Log.e("TASKREQERR", "task request error: $error")
            }
        )
        this.queue.add(jsonRequest)
    }

    fun JSONArrayToTaskList(jsonArray: JSONArray): ArrayList<Task> {
        val taskList = ArrayList<Task>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val task = Task(
                jsonObject.getInt("id"),
                jsonObject.getBoolean("is_urgent"),
                jsonObject.getString("description"),
                jsonObject.getBoolean("is_done")
            )
            taskList.add(task)
        }
        return taskList
    }

    fun deleteTask(task: Task) {
        val jsonArrayRequest = JsonObjectRequest(
            Request.Method.DELETE, URL + DELETE + '/' + task.id,
            this.toJSON(task),
            { response ->
                Log.d("RequestResponse", response.toString())
                this.tasksRequest()
            },
            { volleyError ->
                Log.e("RequestTaskError", "Connection error. $volleyError")
            }
        )

        this.queue.add(jsonArrayRequest)
    }

    fun updateTask(task: Task, state: String) {
        val jsonArrayRequest = JsonObjectRequest(
            Request.Method.PUT, URL + UPDATE + '/' + state + '/' + task.id,
            this.toJSON(task),
            { response ->
                Log.d("RequestResponse", response.toString())
                this.tasksRequest()
            },
            { volleyError ->
                Log.e("RequestTaskError", "Connection error. $volleyError")
            }
        )

        this.queue.add(jsonArrayRequest)
    }

    fun createTask(task: Task) {
        val jsonArrayRequest = JsonObjectRequest(
            Request.Method.POST, URL + POST,
            this.toJSON(task),
            { response ->
                Log.d("RequestResponse", response.toString())
                this.tasksRequest()
            },
            { volleyError ->
                Log.e("RequestTaskError", "Connection error. $volleyError")
            }
        )

        this.queue.add(jsonArrayRequest)
    }

    fun toJSON(task: Task): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("description", task.description)
        jsonObject.put("is_done", task.is_done)
        jsonObject.put("is_urgent", task.is_urgent)

        return jsonObject
    }


}