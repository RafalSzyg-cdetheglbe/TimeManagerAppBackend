package com.start.timemanager.service.interfaces;

import com.start.timemanager.dto.UserDTO;
import com.start.timemanager.model.User;

import java.util.List;

public interface IUserService {
    public List<User> getUsers();

    public User getUser(String username);

    // public void loginUser(User user);

    public void deleteUser(Long userId, String username);

    public void editUser(UserDTO userDTO, Long id);

    public void editOwnUser(UserDTO userDTO, String username);

}
