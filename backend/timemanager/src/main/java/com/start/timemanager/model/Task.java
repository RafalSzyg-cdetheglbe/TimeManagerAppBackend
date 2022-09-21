package com.start.timemanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TASKS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    private LocalDateTime deadline;
    @JsonProperty("task_realization_time")
    private int taskRealizationTime;
    @ManyToOne()
    @JoinColumn(name = "priority_id")
    private Priority priority;
    @JsonProperty("last_change")
    private LocalDateTime lastChange;
    @JsonProperty("estimated_end_time")
    private LocalDateTime estimatedEndTime;
    private boolean isDeleted;

    public Task(String name, String description, User user, Bucket bucket,
            State state, LocalDateTime createdDate, LocalDateTime deadline,
            int taskRealizationTime, Priority priority,
            LocalDateTime lastChange, LocalDateTime estimatedEndTime, boolean isDeleted) {

        this.name = name;
        this.description = description;
        this.user = user;
        this.bucket = bucket;
        this.state = state;
        this.createdDate = createdDate;

        this.deadline = deadline;
        this.taskRealizationTime = taskRealizationTime;
        this.priority = priority;
        this.lastChange = lastChange;
        this.estimatedEndTime = estimatedEndTime;
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
