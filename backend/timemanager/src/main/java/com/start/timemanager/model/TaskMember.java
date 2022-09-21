package com.start.timemanager.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TASK_MEMBERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(targetEntity = com.start.timemanager.model.Task.class)
    @JoinColumn(name = "task_id")
    private Task task;
    private LocalDateTime notificationDate;
    private boolean isDeleted;

    public TaskMember(User user, Task task, LocalDateTime notificationDate, boolean isDeleted) {
        this.user = user;
        this.task = task;
        this.notificationDate = notificationDate;
        this.isDeleted = isDeleted;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
