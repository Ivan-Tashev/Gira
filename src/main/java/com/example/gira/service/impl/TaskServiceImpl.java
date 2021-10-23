package com.example.gira.service.impl;

import com.example.gira.model.binding.TaskAddBindModel;
import com.example.gira.model.entity.TaskEntity;
import com.example.gira.model.entity.enums.ProgressEnum;
import com.example.gira.model.service.TaskServiceModel;
import com.example.gira.model.view.TaskViewModel;
import com.example.gira.repo.ClassificationRepo;
import com.example.gira.repo.TaskRepo;
import com.example.gira.repo.UserRepo;
import com.example.gira.security.CurrentUser;
import com.example.gira.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final ModelMapper modelMapper;
    private final TaskRepo taskRepo;
    private final ClassificationRepo classificationRepo;
    private final CurrentUser currentUser;
    private final UserRepo userRepo;

    public TaskServiceImpl(ModelMapper modelMapper, TaskRepo taskRepo, ClassificationRepo classificationRepo, CurrentUser currentUser, UserRepo userRepo) {
        this.modelMapper = modelMapper;
        this.taskRepo = taskRepo;
        this.classificationRepo = classificationRepo;
        this.currentUser = currentUser;
        this.userRepo = userRepo;
    }

    @Override
    public TaskServiceModel addTask(TaskAddBindModel taskAddBindModel) {
        TaskServiceModel taskServiceModel = modelMapper.map(taskAddBindModel, TaskServiceModel.class)
                .setProgress(ProgressEnum.OPEN)
                .setClassification(classificationRepo.findByClassificationName(taskAddBindModel.getClassification()))
                .setUser(userRepo.findById(currentUser.getId()).orElseThrow());

        TaskEntity savedTaskEntity = taskRepo.save(modelMapper.map(taskServiceModel, TaskEntity.class));
        return modelMapper.map(savedTaskEntity, TaskServiceModel.class);
    }

    @Override
    public boolean existTask(TaskAddBindModel taskAddBindModel) {
        return taskRepo.findByName(taskAddBindModel.getName()) != null;
    }

    @Override
    public List<TaskViewModel> getAllTasks() {

        List<TaskEntity> allTaskEntities = taskRepo.findAll();

        return allTaskEntities.stream()
                .map(taskEntity ->
                        modelMapper.map(taskEntity, TaskViewModel.class)
                                .setAssignedTo(taskEntity.getUser().getUsername())
                                .setClassification(taskEntity.getClassification().getClassificationName()))
                .collect(Collectors.toList());
    }

    @Override
    public TaskServiceModel findTaskByNameSetProgress(String task) {
        TaskEntity taskEntity = taskRepo.findByName(task);

        switch (taskEntity.getProgress().name()) {
            case "OPEN":
                taskEntity.setProgress(ProgressEnum.IN_PROGRESS);
                taskRepo.save(taskEntity);
                break;
            case "IN_PROGRESS":
                taskEntity.setProgress(ProgressEnum.COMPLETED);
                taskRepo.save(taskEntity);
                break;
            case "COMPLETED":
                taskRepo.delete(taskEntity);
                break;
        }
        return modelMapper.map(taskEntity, TaskServiceModel.class);
    }
}
