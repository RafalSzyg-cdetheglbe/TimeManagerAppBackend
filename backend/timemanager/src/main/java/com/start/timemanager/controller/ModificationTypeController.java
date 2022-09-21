package com.start.timemanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.start.timemanager.model.ModificationType;

import com.start.timemanager.service.implementation.ModificationTypeService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ModificationTypeController {
    private final ModificationTypeService modificationTypeService;

    @GetMapping("/modification_types")
    public List<ModificationType> getModificationTypes() {
        return this.modificationTypeService.getModificationTypes();
    }

    @GetMapping("/modification_types/{id}")
    public Optional<ModificationType> getModificationType(@PathVariable("id") Long id) {

        return this.modificationTypeService.getModificationType(id);
    }
}
