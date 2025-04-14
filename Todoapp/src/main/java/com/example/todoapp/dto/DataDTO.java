package com.example.todoapp.dto;

public class DataDTO {
    private int id;
    private String todo;

    public DataDTO() {}

    public DataDTO(int id, String todo) {
        this.id = id;
        this.todo = todo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
