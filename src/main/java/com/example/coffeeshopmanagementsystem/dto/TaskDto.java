package com.example.coffeeshopmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto {
    private Long id;
    private String description;
    private Duration duration;

    /*
    Json format :

    {
  "id": 0,
  "description": "string",
  "duration": "PT4H30M"
    }

    ISO-8601 Duration Format
     */
}
