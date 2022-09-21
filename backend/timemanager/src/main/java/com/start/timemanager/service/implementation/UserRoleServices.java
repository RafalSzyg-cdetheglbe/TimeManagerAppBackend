package com.start.timemanager.service.implementation;

import java.util.List;
import java.util.Optional;

import com.start.timemanager.service.interfaces.IUserRoleService;
import com.start.timemanager.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import com.start.timemanager.model.UserRole;

@Service
public class UserRoleServices implements IUserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServices(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRole> getUsersRoles() {
        return this.userRoleRepository.findAll();
    }

    public Optional<UserRole> getUsersRole(Long id) {
        return this.userRoleRepository.findById(id);
    }
}
