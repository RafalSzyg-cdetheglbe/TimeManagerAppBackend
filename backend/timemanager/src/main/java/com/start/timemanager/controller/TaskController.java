package com.start.timemanager.controller;

import com.start.timemanager.dto.TaskDTO;

import com.start.timemanager.model.Task;

import org.springframework.web.bind.annotation.*;
import com.start.timemanager.service.implementation.TaskService;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable("id") Long id , Principal principal) {
        return this.taskService.getTask(id, principal.getName());
    }

    @GetMapping("/tasks")
    public List<Task> getTasks(@RequestParam Long bucket_id) {
        return this.taskService.getTasks(bucket_id);
    }

    @GetMapping("/tasks/del")
    public List<Task> getDeletedTasks(@RequestParam Long bucket_id) {
        return this.taskService.getDeletedTasks(bucket_id);
    }

    @GetMapping("/tasks/outside")

    public List<Task> getOutsideTasks(Principal principal) {

        return this.taskService.getOutsideTasks(principal.getName());
    }

    @PostMapping("/tasks")
    @ResponseBody
    public TaskDTO addTask(@RequestBody TaskDTO taskDTO, Principal principal) {

      return this.taskService.addTask(taskDTO, principal.getName());
    }

    @PutMapping("/tasks/{id}")

    public TaskDTO editTask(@RequestBody TaskDTO taskDTO, @PathVariable("id") Long id, Principal principal) {
       return this.taskService.editTask(taskDTO, id, principal.getName());

    }

    @DeleteMapping("/tasks/{id}")
    public Long deleteTask(@PathVariable("id") Long id, Principal principal) {
        return taskService.deleteTask(id, principal.getName());
    }



    @GetMapping("/tasks/all")
    @ResponseBody
    public List<TaskDTO> getAllTask(){
return this.taskService.getAllTasks();
    }

}
