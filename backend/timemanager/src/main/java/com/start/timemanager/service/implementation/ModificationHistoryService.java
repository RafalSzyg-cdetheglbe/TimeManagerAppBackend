package com.start.timemanager.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.start.timemanager.service.interfaces.IModificationHistoryService;
import org.springframework.stereotype.Service;

import com.start.timemanager.model.ModificationHistory;
import com.start.timemanager.model.ModificationObject;
import com.start.timemanager.model.ModificationType;
import com.start.timemanager.model.User;
import com.start.timemanager.repository.ModificationHistoryRepository;

@Service
public class ModificationHistoryService implements IModificationHistoryService {
    private final ModificationHistoryRepository modificationHistoryRepository;

    public ModificationHistoryService(ModificationHistoryRepository modificationHistoryRepository) {
        this.modificationHistoryRepository = modificationHistoryRepository;
    }

    public List<ModificationHistory> getModificationHistory() {
        return this.modificationHistoryRepository.findAll();
    }

    public Optional<ModificationHistory> getModificationHistoryElement(Long id) {
        return this.modificationHistoryRepository.findById(id);
    }

    public void saveHistory(Long userId, Long modificationTypeId, Long modificationObjectId, Long objectId,
            LocalDateTime time) {
        User user = new User();
        user.setId(userId);
        ModificationType modificationType = new ModificationType();
        modificationType.setId(modificationTypeId);
        ModificationObject modificationObject = new ModificationObject();
        modificationObject.setId(modificationObjectId);
        modificationHistoryRepository.save(
                new ModificationHistory(time, user, modificationType, modificationObject, objectId));
    }
}
