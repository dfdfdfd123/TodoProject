package com.example.todoapp.service;

import com.example.todoapp.dto.DataDTO;

import java.util.List;

public interface TodoService {
    List<DataDTO> getAllTodos();
    void insertTodo(DataDTO dto);
    void deleteTodo(int id);
}

