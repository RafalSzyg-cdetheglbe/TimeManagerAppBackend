package com.start.timemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.start.timemanager.model.ModificationHistory;

@Repository
public interface ModificationHistoryRepository extends JpaRepository<ModificationHistory, Long> {

}
