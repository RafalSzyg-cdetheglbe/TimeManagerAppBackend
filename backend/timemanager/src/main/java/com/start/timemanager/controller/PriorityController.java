package com.start.timemanager.controller;

import com.start.timemanager.model.Priority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.start.timemanager.service.implementation.PriorityService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PriorityController {
    private final PriorityService priorityService;

    @GetMapping("/priorities/{id}")
    public Priority getPriority(@PathVariable("id")Long id)
    {
        return this.priorityService.getPriority(id);
    }

    @GetMapping("/priorities")
    public List<Priority> getPriorities()
    {
        return this.priorityService.getPriorities();
    }
}
