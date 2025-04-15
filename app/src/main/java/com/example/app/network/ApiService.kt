package com.example.app.network

import com.example.app.Data
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/api/todo/list")
    fun getTodoList(): Call<List<Data>>

    @POST("/api/todo/add")
    fun addTodo(@Body todo: Data): Call<Void>

    @DELETE("/api/todo/delete/{id}")
    fun deleteTodo(@Path("id") id: Int): Call<Void>
}
