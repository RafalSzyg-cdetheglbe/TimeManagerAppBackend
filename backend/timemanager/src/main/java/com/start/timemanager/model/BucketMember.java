package com.start.timemanager.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BUCKET_MEMBERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;
    private boolean isDeleted;

    public BucketMember(User user, Bucket bucket, boolean isDeleted) {
        this.user = user;
        this.bucket = bucket;
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
