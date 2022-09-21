package com.start.timemanager.service.implementation;

import com.start.timemanager.model.Task;
import com.start.timemanager.model.TaskMember;
import com.start.timemanager.repository.TaskMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EmailService {

    final int FIXED_SENDING_RATE = 60 * 10000;
    final String APPLICATION_EMAIL = "autotimemanager@gmail.com";
    private final TaskMemberRepository taskMemberRepository;

    @Autowired
    private JavaMailSender mailSender;

    public EmailService(TaskMemberRepository taskMemberRepository) {
        this.taskMemberRepository = taskMemberRepository;
    }

    public void sendEmailNotification(long interval, String taskName, String taskDescription, String email) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(APPLICATION_EMAIL);
        message.setTo(email);
        message.setSubject("Powiadomienie o zbliżającym się deadline");
        if (interval != 12L)
            message.setText("Zbliża się deadline za: " + interval + " dni\nDla zadania: " + taskName + "\nOpis: "
                    + taskDescription);
        else
            message.setText("Zbliża się deadline za: " + interval + " godzin\nDla zadania: " + taskName + "\nOpis: "
                    + taskDescription);
        mailSender.send(message);
    }

    @Transactional
    @Scheduled(fixedRate = FIXED_SENDING_RATE)
    public void checkNotification() {
        TaskMember taskMember;
        Task task;

        List<TaskMember> taskMembers = this.taskMemberRepository.findByNullNotificationDate();
        int membersAmount = taskMembers.size();

        for (int i = 0; i <= membersAmount - 1; i++) {

            taskMember = taskMembers.get(i);
            task = taskMembers.get(i).getTask();

            LocalDateTime deadline = task.getDeadline();
            LocalDateTime currentDate = LocalDateTime.now();
            Long priorityId = task.getPriority().getId();
            Long hours = ChronoUnit.HOURS.between(currentDate, deadline);
            Long days = ChronoUnit.DAYS.between(currentDate, deadline);

            if (priorityId == 3 && days <= 3) {
                String taskName = task.getName();
                String taskDescription = task.getDescription();
                String email = taskMember.getUser().getEmail();
                sendEmailNotification(days, taskName, taskDescription, email);
                taskMember.setNotificationDate(LocalDateTime.now());
                taskMemberRepository.save(taskMember);
            }
            if (priorityId == 2 && days <= 2) {
                String taskName = task.getName();
                String taskDescription = task.getDescription();
                String email = taskMember.getUser().getEmail();
                sendEmailNotification(days, taskName, taskDescription, email);
                taskMember.setNotificationDate(LocalDateTime.now());
                taskMemberRepository.save(taskMember);
            }
            if (priorityId == 1 && hours <= 12) {
                String taskName = task.getName();
                String taskDescription = task.getDescription();
                String email = taskMember.getUser().getEmail();
                sendEmailNotification(days, taskName, taskDescription, email);
                taskMember.setNotificationDate(LocalDateTime.now());
                taskMemberRepository.save(taskMember);
            }

        }

    }

}
