package com.start.timemanager.service.implementation;

import com.start.timemanager.service.interfaces.IPriorityService;
import com.start.timemanager.model.Priority;
import com.start.timemanager.repository.PriorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriorityService implements IPriorityService {

    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }


    public Priority getPriority(Long id) {
        return priorityRepository.findById(id).get();
    }

    public List<Priority> getPriorities() {
        return priorityRepository.findAll();
    }
}
