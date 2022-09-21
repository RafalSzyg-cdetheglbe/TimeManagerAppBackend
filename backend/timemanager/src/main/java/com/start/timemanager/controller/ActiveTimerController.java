package com.start.timemanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.start.timemanager.dto.ActiveTimerDTO;
import com.start.timemanager.model.ActiveTimer;
import com.start.timemanager.service.implementation.ActiveTimerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ActiveTimerController {
    private final ActiveTimerService activeTimerService;

    @GetMapping("/timers/{id}")
    public ActiveTimerDTO getTimer(@PathVariable("id") Long id, Principal principal) {
        return this.activeTimerService.getTimer(id, principal.getName());
    }

    @GetMapping("/timers/all")
    public List<ActiveTimerDTO> getAllTimers() {
        return this.activeTimerService.getAllTimers();
    }

    @GetMapping("/timers/all/active")
    public List<ActiveTimerDTO> getActiveTimers() {
        return this.activeTimerService.getActiveTimers();
    }

    @GetMapping("/users/timers")
    public List<ActiveTimerDTO> getUserTimers(Principal principal) {
        return this.activeTimerService.getAllUserActiveTimers(principal.getName());
    }

    @GetMapping("/buckets/timers")
    public List<ActiveTimerDTO> getBucketTimers(@RequestParam("bucket_id") Long bucketId) {
        return this.activeTimerService.getAllBucketActiveTimers(bucketId);
    }

    @GetMapping("/tasks/timers")
    public List<ActiveTimerDTO> getTaskTimers(@RequestParam("task_id") Long taskId) {
        return this.activeTimerService.getAllTaskActiveTimers(taskId);
    }

    @GetMapping("/users/tasks/timers")
    public List<ActiveTimerDTO> getUserActiveTimersInTask(@RequestParam("task_id") Long taskId, Principal principal) {
        return this.activeTimerService.getUserActiveTimersInTask(taskId, principal.getName());
    }

    @PostMapping("/timers")
    public ActiveTimerDTO addNewTimer(@RequestBody ActiveTimerDTO activeTimerDTO, Principal principal) {
        return this.activeTimerService.createTimer(activeTimerDTO, principal.getName());
    }

    @PutMapping("/timers/start/{id}")
    public ActiveTimerDTO startTimer(@PathVariable("id") Long id, Principal principal) {
        return this.activeTimerService.startTimer(id, principal.getName());
    }

    @PutMapping("/timers/pause/{id}")
    public ActiveTimerDTO pauseTimer(@PathVariable("id") Long id, Principal principal) {
        return this.activeTimerService.pauseTimer(id, principal.getName());
    }

    @DeleteMapping("/timers/{id}")
    public ActiveTimerDTO stopTimer(@PathVariable("id") Long id, Principal principal) {
        return this.activeTimerService.stopTimer(id, principal.getName());
    }
}
