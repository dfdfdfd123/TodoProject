package com.example.app

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var todoId: Int = -1
    private lateinit var currentItem: Data



    companion object {
        private const val TAG = "Adapter"
    }

    private val items = ArrayList<Data>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val layoutTodo: LinearLayout = itemView.findViewById(R.id.layoutTodo)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        private val editText: EditText = itemView.findViewById(R.id.editText)
        private val editButton: Button = itemView.findViewById(R.id.editButton)

        private var isEditing = false

        init {


                deleteButton.setOnClickListener {
                val todoText = checkBox.text.toString()
                deleteToDo(todoText)
                Toast.makeText(it.context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }

            editButton.setOnClickListener {
                if (!isEditing) {
                    // 수정 모드로 진입
                    isEditing = true
                    checkBox.visibility = View.GONE
                    editText.visibility = View.VISIBLE
                    editText.setText(checkBox.text)
                    editButton.text = "완료"
                }
                else {
                    // 완료 → 서버 업데이트 요청
                    isEditing = false
                    val updatedText = editText.text.toString()
                    val updatedItem = currentItem.copy(todo = updatedText)

//                    수정 (작업 중)
                 updateTodoStatus(updatedItem)
                    checkBox.text = updatedText
                    currentItem = updatedItem
                    checkBox.visibility = View.VISIBLE
                    editText.visibility = View.GONE
                    editButton.text = "수정"

                }
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

        private fun updateTodoStatus(item: Data) {
            RetrofitClient.apiService.updateTodo(item.id, item).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.d(TAG, "업데이트 요청: ${item.id}, ${item.todo}, isDone: ${item.isDone}")
                    Toast.makeText(layoutTodo.context, "상태 업데이트 성공", Toast.LENGTH_SHORT).show()

                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(layoutTodo.context, "업데이트 실패: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        private fun applyCompletionStyle(isDone: Boolean) {
            if (isDone) {
                // 완료 상태: 텍스트에 취소선 + 회색 처리
                checkBox.paintFlags = checkBox.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                checkBox.setTextColor(android.graphics.Color.GRAY)
            } else {
                // 미완료 상태: 취소선 제거 + 원래 색상(예: 검정색)
                checkBox.paintFlags = checkBox.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                checkBox.setTextColor(android.graphics.Color.BLACK)
            }
        }


        fun setItem(item: Data) {
            todoId = item.id
            currentItem = item
            checkBox.text = item.todo
            checkBox.isChecked = item.isDone

            applyCompletionStyle(item.isDone)

            checkBox.setOnCheckedChangeListener(null) // 기존 리스너 제거
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val updatedItem = item.copy(isDone = isChecked)
                updateTodoStatus(updatedItem)
                applyCompletionStyle(isChecked)
            }
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
