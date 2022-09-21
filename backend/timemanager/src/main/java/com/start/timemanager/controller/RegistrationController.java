package com.start.timemanager.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import com.start.timemanager.event.RegistrationCompleteEvent;
import com.start.timemanager.model.Password;
import com.start.timemanager.model.PasswordResetToken;
import com.start.timemanager.model.User;
import com.start.timemanager.model.VerificationToken;
import com.start.timemanager.service.implementation.RegistrationService;
import com.start.timemanager.service.implementation.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping()
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final ApplicationEventPublisher publisher;
    private final UserService userService;

    @PostMapping("/register")
    public void registerNewUser(@RequestBody User user, final HttpServletRequest request) {
        user = this.registrationService.registerUser(user);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam String token) {
        if (registrationService.validateVerificationToken(token).equalsIgnoreCase("valid"))
            return "user verifies successfully";

        return "Bad user";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = registrationService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "verification Link Sent";
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
        log.info("Click the link to verify your account: {}", url);
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody Password password, HttpServletRequest request) {
        PasswordResetToken passwordResetToken = registrationService.generateTokenForResetPassword(password);
        if (passwordResetToken != null)
            return passwordResetTokenMail(passwordResetToken.getUser(), applicationUrl(request),
                    passwordResetToken.getToken());
        return null;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam String token, @RequestBody Password password) {

        String result = registrationService.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid"))
            return "Invalid Token";

        Optional<User> user = registrationService.getUserByPasswordResetToken(token);
        if (user.isPresent()) {
            registrationService.resetPassword(user.get(), password.getNewPassword());
            return "Password Reset Successfully";
        } else {
            return "Invalid Token";
        }
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/savePassword?token=" + token;
        log.info("Click the link to reset your password: {}", url);
        return url;
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody Password password) {
        User user = userService.findUserByEmail(password.getEmail());
        if (!registrationService.checkValidOldPassword(user, password.getOldPassword()))
            return "Invalid old password";
        registrationService.resetPassword(user, password.getNewPassword());
        return "password changed successfully";
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        //filtrowanie
        this.registrationService.registerUser(user);
    }

    @PostMapping("/client")
    public void addClient(@RequestBody User user) {
        this.registrationService.registerClient(user);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
