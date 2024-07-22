package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.TaskDto;
import com.example.coffeeshopmanagementsystem.entity.Task;
import com.example.coffeeshopmanagementsystem.exception.DataIntegrityException;
import com.example.coffeeshopmanagementsystem.exception.entities.ServiceException;
import com.example.coffeeshopmanagementsystem.mapper.TaskMapper;
import com.example.coffeeshopmanagementsystem.repository.TaskRepository;
import com.example.coffeeshopmanagementsystem.service.facade.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        return taskMapper.toDto(task);
    }
    @Override
    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        try{
        Task task = taskMapper.toEntity(taskDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Invalid data: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new ServiceException("Failed to create the task: " + e.getMessage(), e);
        }
    }
    @Override
    @Transactional
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        try {
            Task existingTask = taskRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

            existingTask.setDescription(taskDto.getDescription());
            existingTask.setDuration(taskDto.getDuration());

            Task updatedTask = taskRepository.save(existingTask);
            return taskMapper.toDto(updatedTask);
        }catch (Exception e){
            throw new ServiceException("Failed to update the task with id " + id + ": " + e.getMessage(), e);
        }
    }
    @Override
    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
