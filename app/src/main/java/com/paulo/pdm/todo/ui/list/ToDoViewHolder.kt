package com.paulo.pdm.todo.ui.list

import android.view.View
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paulo.pdm.todo.R
import com.paulo.pdm.todo.model.ToDo


class ToDoViewHolder(itens: View, private val adapter: ToDoAdapter): RecyclerView.ViewHolder(itens){

    private val frame_isUrgent: FrameLayout = itens.findViewById(R.id.left_color)
    private val txt_description: TextView = itens.findViewById(R.id.txt_description)
    private val chx_done: CheckBox = itens.findViewById(R.id.cbx_isDone)
    private lateinit var currentToDo: ToDo
    init {
        itemView.setOnClickListener {
            this.adapter.getOnClickToDoListener()?.onClickTask(this.currentToDo);
        }
        this.chx_done.setOnCheckedChangeListener { _, b ->
            this.adapter.getOnCheckBoxClickToDoListener()?.onCheckBoxClickToDo(this.currentToDo, b)
        }
    }
    fun bind(task: ToDo){
        this.currentToDo = task
        this.txt_description.text = this.currentToDo.description
        this.chx_done.isChecked = this.currentToDo.isDone
        if (this.currentToDo.isurgent){
            this.frame_isUrgent.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.red))
        } else {
            this.frame_isUrgent.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.green))
        }
    }

}
