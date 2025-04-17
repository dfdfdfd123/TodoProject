package com.example.app

data class Data(
    var id: Int,
    var todo: String,
    val isDone: Boolean = false,
//    val createdAt: String? = null
)
