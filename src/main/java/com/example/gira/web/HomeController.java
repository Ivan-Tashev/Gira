package com.example.gira.web;

import com.example.gira.model.view.TaskViewModel;
import com.example.gira.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    private final TaskService taskService;

    public HomeController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        List<TaskViewModel> allTasks = taskService.getAllTasks();
        model.addAttribute("allTasks", allTasks);
        return "home";
    }

    @GetMapping("/home/progress/{task}")
    public String progressTask(@PathVariable String task){
        taskService.findTaskByNameSetProgress(task);


        return "redirect:/home";
    }


}
