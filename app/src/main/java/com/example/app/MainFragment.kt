package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false) as ViewGroup

        initUI(rootView)

        return rootView
    }

    private fun initUI(rootView: ViewGroup) {
        recyclerView = rootView.findViewById(R.id.recyclerView)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        adapter = Adapter()
        recyclerView.adapter = adapter

        loadTodoList()
    }

    // 리스트 불러오기
     fun loadTodoList() {
        RetrofitClient.apiService.getTodoList().enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    val todoList = response.body() ?: emptyList()
                    adapter.setItems(todoList)
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Log.e("MainFragment", "서버 오류: ${t.message}")
            }
        })
    }
}

