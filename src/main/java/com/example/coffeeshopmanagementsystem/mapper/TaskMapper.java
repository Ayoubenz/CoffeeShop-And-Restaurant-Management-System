package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.TaskDto;
import com.example.coffeeshopmanagementsystem.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Duration;

@Mapper
public interface TaskMapper {

    @Mapping(source = "duration", target = "duration", qualifiedByName = "durationToString")
    TaskDto toDto(Task task);
    @Mapping(source = "duration", target = "duration", qualifiedByName = "stringToDuration")
    Task toEntity(TaskDto taskDto);

    @Named("stringToDuration")
    default Duration stringToDuration(String durationStr) {
        return Duration.parse(durationStr);
    }

    @Named("durationToString")
    default String durationToString(Duration duration) {
        return duration.toString();
    }
}
