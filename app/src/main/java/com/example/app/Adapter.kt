package com.example.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            val item = items.find { it.todo == todo } ?: return
            RetrofitClient.apiService.deleteTodo(item.id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(layoutTodo.context, "삭제 성공!", Toast.LENGTH_SHORT).show()
                    items.remove(item)
                    notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(layoutTodo.context, "삭제 실패: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
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
