package com.example.gira.service;

import com.example.gira.model.binding.TaskAddBindModel;
import com.example.gira.model.service.TaskServiceModel;
import com.example.gira.model.view.TaskViewModel;

import java.util.List;

public interface TaskService {
    TaskServiceModel addTask(TaskAddBindModel taskAddBindModel);

    boolean existTask(TaskAddBindModel taskAddBindModel);

    List<TaskViewModel> getAllTasks();

    TaskServiceModel findTaskByNameSetProgress(String task);
}
