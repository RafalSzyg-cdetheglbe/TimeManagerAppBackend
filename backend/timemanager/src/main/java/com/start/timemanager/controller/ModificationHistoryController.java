package com.start.timemanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.start.timemanager.model.ModificationHistory;
import com.start.timemanager.service.implementation.ModificationHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ModificationHistoryController {
    private final ModificationHistoryService modificationHistoryService;

    @GetMapping("/modification_history")
    public List<ModificationHistory> getModificationHistory() {
        return this.modificationHistoryService.getModificationHistory();
    }

    @GetMapping("/modification_history/{id}")
    public Optional<ModificationHistory> getModificationHistoryElement(@PathVariable("id") Long id) {
        return this.modificationHistoryService.getModificationHistoryElement(id);
    }
}
