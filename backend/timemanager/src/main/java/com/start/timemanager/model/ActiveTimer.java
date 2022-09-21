package com.start.timemanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ACTIVE_TIMERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveTimer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = com.start.timemanager.model.Task.class)
    @JoinColumn(name = "task_id")
    @JsonProperty("task_id")
    private Task task;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonProperty("user_id")
    private User user;
    private LocalDateTime startTime;
    private LocalDateTime startPause;
    private boolean isPaused;
    private int timerRealizationTime;
    private boolean isDeleted;

    public ActiveTimer(Task task, User user, LocalDateTime startTime, LocalDateTime startPause, boolean isPaused,
            int timerRealizationTime, boolean isDeleted) {
        this.task = task;
        this.user = user;
        this.startTime = startTime;
        this.startPause = startPause;
        this.isPaused = isPaused;
        this.timerRealizationTime = timerRealizationTime;
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
