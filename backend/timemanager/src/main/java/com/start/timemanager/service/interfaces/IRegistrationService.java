package com.start.timemanager.service.interfaces;

import java.util.Optional;

import com.start.timemanager.model.Password;
import com.start.timemanager.model.PasswordResetToken;
import com.start.timemanager.model.User;
import com.start.timemanager.model.VerificationToken;

public interface IRegistrationService {
    public void registerClient(User user);

    public User registerUser(User user);

    public void saveVerificationTokenForUser(String token, User user);

    public String validateVerificationToken(String token);

    public VerificationToken generateNewVerificationToken(String oldToken);

    public PasswordResetToken generateTokenForResetPassword(Password password);

    public String validatePasswordResetToken(String token);

    public Optional<User> getUserByPasswordResetToken(String token);

    public void resetPassword(User user, String newPassword);

    public boolean checkValidOldPassword(User user, String oldPassword);
}
