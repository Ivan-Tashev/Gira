package com.example.gira.web;

import com.example.gira.model.binding.TaskAddBindModel;
import com.example.gira.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String getAddTaskPage(Model model) {
        if (!model.containsAttribute("taskAddBindModel")) {
            model.addAttribute("taskAddBindModel", new TaskAddBindModel())
                    .addAttribute("existingTask", false);
        }
        return "add-task";
    }

    @PostMapping("/add")
    public String addTask(@Valid TaskAddBindModel taskAddBindModel,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // check for Input errors
        if (bindingResult.hasErrors() || taskService.existTask(taskAddBindModel)) {
            redirectAttributes.addFlashAttribute("taskAddBindModel", taskAddBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.taskAddBindModel", bindingResult);
            // check for Unique task name
            if (taskService.existTask(taskAddBindModel)) {
                redirectAttributes.addFlashAttribute("existingTask", true);
            }
            return "redirect:/tasks/add";
        }
        // save/add Task to database
        taskService.addTask(taskAddBindModel);
        return "redirect:/home";
    }
}

