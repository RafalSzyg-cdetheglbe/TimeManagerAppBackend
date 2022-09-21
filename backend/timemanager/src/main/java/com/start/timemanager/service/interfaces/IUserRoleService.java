package com.start.timemanager.service.interfaces;

import com.start.timemanager.model.UserRole;

import java.util.List;
import java.util.Optional;

public interface IUserRoleService {
    public List<UserRole> getUsersRoles();

    public Optional<UserRole> getUsersRole(Long id);
}
