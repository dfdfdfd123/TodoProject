package com.example.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class DataDTO {
    private int id;
    private String todo;

    @JsonProperty("isDone")
    private Boolean isDone;
}


//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;
