package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks();
    TaskDto getTaskById(Long id);
    TaskDto createTask(TaskDto taskDto);
    TaskDto updateTask(Long id, TaskDto taskDto);
    void deleteTask(Long id);
}
