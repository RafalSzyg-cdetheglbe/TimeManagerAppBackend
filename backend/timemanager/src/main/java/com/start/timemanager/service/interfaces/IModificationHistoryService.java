package com.start.timemanager.service.interfaces;

import com.start.timemanager.model.ModificationHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IModificationHistoryService {
    public List<ModificationHistory> getModificationHistory();

    public Optional<ModificationHistory> getModificationHistoryElement(Long id);

    public void saveHistory(Long userId, Long modificationTypeId, Long modificationObjectId, Long objectId,
            LocalDateTime time);
}
