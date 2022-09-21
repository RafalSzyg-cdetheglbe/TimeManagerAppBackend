package com.start.timemanager.controller;

import com.start.timemanager.model.State;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.start.timemanager.service.implementation.StateService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @GetMapping("/states/{id}")
    public State getState(@PathVariable("id")Long id){
        return this.stateService.getState(id);
    }

    @GetMapping("/states")
    public List<State> getStates(){
        return this.stateService.getStates();
    }
}
