package com.paulo.pdm.todo.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulo.pdm.todo.R
import com.paulo.pdm.todo.model.ToDo


class ToDoAdapter (private var toDo: ArrayList<ToDo>): RecyclerView.Adapter<ToDoViewHolder>() {
    private var listener: OnClickToDoListener? = null
    private var cbx_listener: OnCheckBoxClickToDoListener? = null


    fun interface OnClickToDoListener {
        fun onClickTask(task: ToDo)
    }

    fun interface OnCheckBoxClickToDoListener{
        fun onCheckBoxClickToDo(task: ToDo, b: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutRes = R.layout.todo
        val itemView = layoutInflater.inflate(layoutRes, parent, false)
        return ToDoViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(this.toDo[position])
    }

    override fun getItemCount(): Int {
        return this.toDo.size
    }

    fun getOnClickToDoListener(): OnClickToDoListener? {
        return this.listener
    }

    fun getOnCheckBoxClickToDoListener(chxListener: OnCheckBoxClickToDoListener?) {
        this.cbx_listener = chxListener
    }


    fun setOnCheckBoxClickToDoListener(chxListener: OnCheckBoxClickToDoListener?) {
        this.cbx_listener = chxListener
    }

    fun getOnCheckBoxClickToDoListener(): OnCheckBoxClickToDoListener? {
        return this.cbx_listener
    }

}
