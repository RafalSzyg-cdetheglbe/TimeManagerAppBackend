package com.start.timemanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.start.timemanager.model.UserRole;

import com.start.timemanager.service.implementation.UserRoleServices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleServices userRoleServices;

    @GetMapping("/roles")
    public List<UserRole> getUsersRoles() {
        return this.userRoleServices.getUsersRoles();
    }

    @GetMapping("/roles/{id}")
    public Optional<UserRole> getUsersRole(@PathVariable("id") Long id) {
        return this.userRoleServices.getUsersRole(id);
    }

}
