package com.start.timemanager.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "user_premission")
    private UserRole userRole;
    private int activeBuckets;
    private boolean isActive;
    private boolean isDeleted;
    private LocalDateTime createdDate;

    public User(String email, String password, String firstName, String lastName, UserRole userRole,
            int activeBuckets, boolean isActive, boolean isDeleted, LocalDateTime createdDate) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.activeBuckets = activeBuckets;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
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
