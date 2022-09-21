package com.start.timemanager.service.interfaces;

import com.start.timemanager.model.State;

import java.util.List;

public interface IStateService {
    public State getState(Long id);
    public List<State> getStates();
}
