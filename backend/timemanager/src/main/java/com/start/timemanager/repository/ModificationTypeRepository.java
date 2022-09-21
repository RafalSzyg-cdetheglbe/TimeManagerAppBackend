package com.start.timemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.start.timemanager.model.ModificationType;

@Repository
public interface ModificationTypeRepository extends JpaRepository<ModificationType, Long> {

}
