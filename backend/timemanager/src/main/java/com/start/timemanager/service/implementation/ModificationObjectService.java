package com.start.timemanager.service.implementation;

import java.util.List;
import java.util.Optional;

import com.start.timemanager.service.interfaces.IModificationObjectService;
import org.springframework.stereotype.Service;

import com.start.timemanager.model.ModificationObject;
import com.start.timemanager.repository.ModificationObjectRepository;

@Service
public class ModificationObjectService implements IModificationObjectService {

    private final ModificationObjectRepository modificationObjectRepository;

    public ModificationObjectService(ModificationObjectRepository modificationObjectRepository) {
        this.modificationObjectRepository = modificationObjectRepository;
    }

    public List<ModificationObject> getModificationObjects() {
        return modificationObjectRepository.findAll();
    }

    public Optional<ModificationObject> getModificationObject(Long id) {
        return modificationObjectRepository.findById(id);
    }
}
