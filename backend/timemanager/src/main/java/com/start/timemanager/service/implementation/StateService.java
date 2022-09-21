package com.start.timemanager.service.implementation;

import com.start.timemanager.service.interfaces.IStateService;
import com.start.timemanager.model.State;
import com.start.timemanager.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService implements IStateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public State getState(Long id) {
        return this.stateRepository.findById(id).get();
    }

    public List<State> getStates() {
        return this.stateRepository.findAll();
    }
}
