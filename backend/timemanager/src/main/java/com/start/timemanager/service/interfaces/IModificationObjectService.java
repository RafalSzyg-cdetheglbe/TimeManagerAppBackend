package com.start.timemanager.service.interfaces;

import com.start.timemanager.model.ModificationObject;

import java.util.List;
import java.util.Optional;

public interface IModificationObjectService {
    public List<ModificationObject> getModificationObjects();

    public Optional<ModificationObject> getModificationObject(Long id);

}
