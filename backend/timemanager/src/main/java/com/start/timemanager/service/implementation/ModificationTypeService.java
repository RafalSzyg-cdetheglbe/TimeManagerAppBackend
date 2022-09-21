package com.start.timemanager.service.implementation;

import java.util.List;
import java.util.Optional;

import com.start.timemanager.service.interfaces.IModificationTypeService;
import org.springframework.stereotype.Service;

import com.start.timemanager.model.ModificationType;
import com.start.timemanager.repository.ModificationTypeRepository;

@Service
public class ModificationTypeService implements IModificationTypeService {

    private final ModificationTypeRepository modificationTypeRepository;

    public ModificationTypeService(ModificationTypeRepository modificationTypeRepository) {
        this.modificationTypeRepository = modificationTypeRepository;
    }

    public List<ModificationType> getModificationTypes() {
        return modificationTypeRepository.findAll();
    }

    public Optional<ModificationType> getModificationType(Long id) {
        return modificationTypeRepository.findById(id);
    }
}
