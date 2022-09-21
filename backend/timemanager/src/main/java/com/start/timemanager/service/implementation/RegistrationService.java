package com.start.timemanager.service.implementation;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.start.timemanager.error.NotValidExeption;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.start.timemanager.model.Password;
import com.start.timemanager.model.PasswordResetToken;
import com.start.timemanager.model.User;
import com.start.timemanager.model.UserRole;
import com.start.timemanager.model.VerificationToken;
import com.start.timemanager.repository.PasswordResetTokenRepository;
import com.start.timemanager.repository.UserRepository;
import com.start.timemanager.repository.VerificationTokenRepository;
import com.start.timemanager.service.interfaces.IRegistrationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationService implements IRegistrationService {
    private final UserRepository userRepository;
    private final ModificationHistoryService modificationHistoryService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public User registerUser(User user) {

        // User userByEmail = userRepository.findUserByEmail(user.getEmail());

        if (!isValidEmailAddress(user.getEmail())) {
            throw new NotValidExeption("Invalid email");
        }

        LocalDateTime time = LocalDateTime.now();
        UserRole userRole = new UserRole();
        userRole.setId(2L);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(userRole);
        user.setActiveBuckets(0);
        user.setActive(true);
        user.setDeleted(false);
        user.setCreatedDate(time);
        try {
            this.userRepository.save(user);
        }catch (Exception e){
            throw new NotValidExeption("Invalid Credential Error");
        }

        modificationHistoryService.saveHistory(user.getId(), 1L, 1L, user.getId(), time);
        return user;
    }

    public static boolean isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            return false;
        }
        return true;
    }

    public void registerClient(User user) {
        // User userByEmail = userRepository.findUserByEmail(user.getEmail());
        // if (userByEmail.isPresent()) {
        // throw new IllegalStateException("Email Taken");
        // }
        if (!isValidEmailAddress(user.getEmail())) {
            throw new IllegalStateException("Wrong Email");
        }

        LocalDateTime time = LocalDateTime.now();
        UserRole userRole = new UserRole();
        userRole.setId(4L);
        user.setUserRole(userRole);
        user.setActiveBuckets(0);
        user.setActive(false);
        user.setDeleted(false);
        user.setCreatedDate(time);
        this.userRepository.save(user);
        modificationHistoryService.saveHistory(user.getId(), 1L, 1L, user.getId(), time);
    }

    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null)
            return "invalid";
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if (verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        user.setActive(true);
        userRepository.save(user);
        return "valid";
    }

    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public PasswordResetToken generateTokenForResetPassword(Password password) {
        User user = userRepository.findUserByEmail(password.getEmail());
        if (user != null) {
            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
            passwordResetTokenRepository.save(passwordResetToken);
            return passwordResetToken;
        }
        return null;
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null)
            return "invalid";

        Calendar calendar = Calendar.getInstance();
        if (passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }
        return "valid";
    }

    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    public void resetPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public boolean checkValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
}
