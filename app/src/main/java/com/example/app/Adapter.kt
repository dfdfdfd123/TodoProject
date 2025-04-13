package com.example.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    companion object {
        private const val TAG = "Adapter"
    }

    private val items = ArrayList<Data>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val layoutTodo: LinearLayout = itemView.findViewById(R.id.layoutTodo)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        init {
            deleteButton.setOnClickListener {
                val todoText = checkBox.text.toString()
                deleteToDo(todoText)
                Toast.makeText(it.context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        private fun deleteToDo(todo: String) {
            // 실제 DB와 연동되는 삭제 로직을 여기에 구현
        }

        fun setItem(item: Data) {
            checkBox.text = item.todo
        }

        fun setLayout() {
            layoutTodo.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.todo_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
        holder.setLayout()
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<Data>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItem(item: Data) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
}
