package com.start.timemanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.start.timemanager.model.ModificationObject;
import com.start.timemanager.service.implementation.ModificationObjectService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ModificationObjectController {
    private final ModificationObjectService modificationObjectService;

    @GetMapping("/modification_objects")
    public List<ModificationObject> getModificationObjects() {
        return this.modificationObjectService.getModificationObjects();
    }

    @GetMapping("/modification_objects/{id}")
    public Optional<ModificationObject> getModificationObject(@PathVariable("id") Long id) {

        return this.modificationObjectService.getModificationObject(id);
    }
}
