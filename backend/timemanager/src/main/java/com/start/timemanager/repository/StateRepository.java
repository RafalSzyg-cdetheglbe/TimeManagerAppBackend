package com.start.timemanager.repository;

import com.start.timemanager.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
