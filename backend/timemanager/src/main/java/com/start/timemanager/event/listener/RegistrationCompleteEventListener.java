package com.start.timemanager.event.listener;

import java.util.UUID;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.start.timemanager.event.RegistrationCompleteEvent;
import com.start.timemanager.model.User;
import com.start.timemanager.service.implementation.RegistrationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final RegistrationService registrationService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        registrationService.saveVerificationTokenForUser(token, user);

        String url = event.getAppUrl() + "/verifyRegistration?token=" + token;
        log.info("Click the link to verify your account: {}", url);
    }

}
