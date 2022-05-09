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
    private lateinit var txtToDo: EditText
    private lateinit var toDo: ArrayList<ToDo>
    private lateinit var rv_toDo: RecyclerView
    private lateinit var toDoAdapter: ToDoAdapter
    private lateinit var switch_isUrgent: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.txtToDo = findViewById(R.id.txt_ToDo)
        this.rv_toDo = findViewById(R.id.txt_ToDo)
        this.switch_isUrgent = findViewById(R.id.txt_ToDo)


        if(savedInstanceState == null){
            this.toDo = ArrayList()
        } else if(savedInstanceState.containsKey("RECOVER_TASKS")){
            this.toDo = savedInstanceState.getParcelableArrayList<ToDo>("RECOVER_TASKS") as ArrayList<ToDo>
        }
        this.toDoAdapter = ToDoAdapter(this.toDo)

        this.toDoAdapter.setOnCheckBoxClickToDoListener  { todo, boolean ->
            todo.isDone = boolean
        }

        this.rv_toDo.layoutManager = LinearLayoutManager(this)
        this.rv_toDo.adapter = this.toDoAdapter


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("RECOVER_TASKS", this.toDo)
    }

    fun onClickAction(view: View){
        val toDo = this.txtToDo.text.toString()
        if (toDo.isNotBlank()) {
            val rv_toDo = ToDo(
                toDo,
                this.switch_isUrgent.isChecked,
                false
            )
            this.toDo.add(rv_toDo)
            this.toDoAdapter.notifyItemInserted(this.toDo.size-1)
            this.rv_toDo.scrollToPosition(this.toDo.size-1)
            this.switch_isUrgent.isChecked = false
            this.txtToDo.text.clear()
        }
    }

}