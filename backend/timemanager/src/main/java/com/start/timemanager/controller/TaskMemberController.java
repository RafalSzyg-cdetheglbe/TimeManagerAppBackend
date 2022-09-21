package com.start.timemanager.controller;

import com.start.timemanager.dto.TaskMemberDTO;
import com.start.timemanager.model.TaskMember;

import org.springframework.web.bind.annotation.*;
import com.start.timemanager.service.implementation.TaskMemberService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskMemberController {
    private final TaskMemberService taskMemberService;

    @GetMapping("/tasks/task_members/{taskid}")
    public List<TaskMember> getTaskMembers(@PathVariable Long taskid) {
        return this.taskMemberService.getTaskMembers(taskid);
    }

    @PostMapping("/tasks/task_members")
    public TaskMemberDTO addTaskMember(@RequestBody TaskMemberDTO taskMemberDTO) {
        return this.taskMemberService.addTaskMember(taskMemberDTO);
    }

    @DeleteMapping(path = "/tasks/task_members/{id}")
    public void deleteTaskMember(@PathVariable("id") Long id) {

        taskMemberService.deleteTaskMember(id);
    }

}
