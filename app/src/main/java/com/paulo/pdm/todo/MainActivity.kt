package com.paulo.pdm.todo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paulo.pdm.todo.model.ToDo
import com.paulo.pdm.todo.ui.list.ToDoAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var txt_ToDo: EditText
    private lateinit var toDo: ArrayList<ToDo>
    private lateinit var rv_toDoList: RecyclerView
    private lateinit var toDoAdapter: ToDoAdapter
    private lateinit var switch_isUrgent: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.txt_ToDo = findViewById(R.id.txt_ToDo)
        this.rv_toDoList = findViewById(R.id.rv_toDoList)
        this.switch_isUrgent = findViewById(R.id.switch_isUrgent)


        if(savedInstanceState == null){
            this.toDo = ArrayList()
        } else if(savedInstanceState.containsKey("RECOVER_TASKS")){
            this.toDo = savedInstanceState.getParcelableArrayList<ToDo>("RECOVER_TASKS") as ArrayList<ToDo>
        }
        this.toDoAdapter = ToDoAdapter(this.toDo)

        this.toDoAdapter.setOnCheckBoxClickToDoListener  { todo, boolean ->
            todo.isDone = boolean
        }

        this.rv_toDoList.layoutManager = LinearLayoutManager(this)
        this.rv_toDoList.adapter = this.toDoAdapter


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("RECOVER_TASKS", this.toDo)
    }

    fun onClickAction(view: View){
        val toDo = this.txt_ToDo.text.toString()
        if (toDo.isNotBlank()) {
            val rv_toDo = ToDo(
                toDo,
                this.switch_isUrgent.isChecked,
                false
            )
            this.toDo.add(rv_toDo)
            this.toDoAdapter.notifyItemInserted(this.toDo.size-1)
            this.rv_toDoList.scrollToPosition(this.toDo.size-1)
            this.switch_isUrgent.isChecked = false
            this.txt_ToDo.text.clear()
        }
    }

}