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
<<<<<<< HEAD
        fun onClickToDo(todo: ToDo)
    }

    fun interface OnCheckBoxClickToDoListener{
        fun onCheckBoxClickToDo(todo: ToDo, b: Boolean)
=======
        fun onClickTask(task: ToDo)
    }

    fun interface OnCheckBoxClickToDoListener{
        fun onCheckBoxClickToDo(task: ToDo, b: Boolean)
>>>>>>> ce3b5c2d8fa90f302787cd030e5b1e0ee6a537f4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
<<<<<<< HEAD
        val layoutRes = R.layout.todo
        val itens = layoutInflater.inflate(layoutRes, parent, false)
        return ToDoViewHolder(itens, this)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, i: Int) {
        holder.bind(this.toDo[i])
=======
        val layoutRes = R.layout.ToDo
        val itemView = layoutInflater.inflate(layoutRes, parent, false)
        return ToDoViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(this.toDo[position])
>>>>>>> ce3b5c2d8fa90f302787cd030e5b1e0ee6a537f4
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


<<<<<<< HEAD
    fun setOnCheckBoxClickToDoListener(chxListener: OnCheckBoxClickToDoListener?) {
        this.cbx_listener = chxListener
    }

=======
>>>>>>> ce3b5c2d8fa90f302787cd030e5b1e0ee6a537f4
    fun getOnCheckBoxClickToDoListener(): OnCheckBoxClickToDoListener? {
        return this.cbx_listener
    }

}
