package com.start.timemanager.service.interfaces;

import com.start.timemanager.model.Priority;

import java.util.List;

public interface IPriorityService {

    public Priority getPriority(Long id);
    public List<Priority> getPriorities();
}
