package com.example.gira.model.service;

import com.example.gira.model.entity.ClassificationEntity;
import com.example.gira.model.entity.UserEntity;
import com.example.gira.model.entity.enums.ProgressEnum;

import java.time.LocalDate;

public class TaskServiceModel {
    private String name;
    private String description;
    private ProgressEnum progress;
    private LocalDate dueDate;
    private ClassificationEntity classification;
    private UserEntity user;

    public String getName() {
        return name;
    }

    public TaskServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProgressEnum getProgress() {
        return progress;
    }

    public TaskServiceModel setProgress(ProgressEnum progress) {
        this.progress = progress;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskServiceModel setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public ClassificationEntity getClassification() {
        return classification;
    }

    public TaskServiceModel setClassification(ClassificationEntity classification) {
        this.classification = classification;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public TaskServiceModel setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
