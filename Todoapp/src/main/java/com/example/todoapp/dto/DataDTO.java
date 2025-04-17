package com.example.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class DataDTO {
    private int id;
    private String todo;

    @JsonProperty("isDone")
    private boolean done;

}
