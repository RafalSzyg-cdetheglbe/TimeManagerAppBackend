package com.start.timemanager.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "BUCKETS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    private int activeTasks;
    private LocalDateTime lastChange;
    private LocalDateTime createdDate;
    private int maxTasks;
    private boolean isDeleted;

    public Bucket(String name, String description, User user, int activeTasks, LocalDateTime lastChange,
            LocalDateTime createdDate,
            int maxTasks, boolean isDeleted) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.activeTasks = activeTasks;
        this.lastChange = lastChange;
        this.createdDate = createdDate;
        this.maxTasks = maxTasks;
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
