package com.start.timemanager.service.interfaces;

import com.start.timemanager.model.ModificationType;

import java.util.List;
import java.util.Optional;

public interface IModificationTypeService {
    public List<ModificationType> getModificationTypes();

    public Optional<ModificationType> getModificationType(Long id);
}
