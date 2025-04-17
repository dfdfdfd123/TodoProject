package com.example.todoapp.controller;

import com.example.todoapp.dto.DataDTO;
import com.example.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@CrossOrigin(origins = "*") // Android에서 요청 받을 수 있도록 설정
public class TodoController {

    @Autowired
    private TodoService todoService;

    // 테스트용 API
    @GetMapping("/ping")
    public String ping() {
        return "서버 연결 성공!";
    }

    //  전체 조회
    @GetMapping("/list")
    public List<DataDTO> getAllTodos() {
        return todoService.getAllTodos();
    }

    //  등록
    @PostMapping("/add")
    public void insertTodo(@RequestBody DataDTO dto) {
        todoService.insertTodo(dto);
    }

    // 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteTodo(@PathVariable int id) {
        todoService.deleteTodo(id);
    }

    @PutMapping("/{id}")
    public void updateTodo(@PathVariable int id, @RequestBody DataDTO dto) {
        dto.setId(id); // id는 path에서 오고, 나머지는 body
        todoService.updateTodo(dto);
    }
}

