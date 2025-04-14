package com.example.todoapp.service;

import com.example.todoapp.dto.DataDTO;
import com.example.todoapp.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoMapper todoMapper;

    @Override
    public List<DataDTO> getAllTodos() {
        return todoMapper.getAllTodos();
    }

    @Override
    public void insertTodo(DataDTO dto) {
        todoMapper.insertTodo(dto);
    }

    @Override
    public void deleteTodo(int id) {
        todoMapper.deleteTodo(id);
    }
}

