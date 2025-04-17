package com.example.todoapp.mapper;

import com.example.todoapp.dto.DataDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapper {
    List<DataDTO> getAllTodos();
    void insertTodo(DataDTO dto);
    void deleteTodo(int id);
    void updateTodo(DataDTO dto);
}

