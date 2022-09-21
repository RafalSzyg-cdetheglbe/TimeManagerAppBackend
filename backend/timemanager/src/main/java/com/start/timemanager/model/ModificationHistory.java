package com.start.timemanager.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MODIFICATION_HISTORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime modificationDate;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(targetEntity = ModificationType.class)
    @JoinColumn(name = "modification_type")
    private ModificationType modificationType;
    @ManyToOne(targetEntity = ModificationObject.class)
    @JoinColumn(name = "object_type")
    private ModificationObject modificationObject;
    private Long objectId;

    public ModificationHistory(LocalDateTime modificationDate, User user, ModificationType modificationType,
            ModificationObject modificationObject, Long objectId) {
        this.modificationDate = modificationDate;
        this.user = user;
        this.modificationType = modificationType;
        this.modificationObject = modificationObject;
        this.objectId = objectId;
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
