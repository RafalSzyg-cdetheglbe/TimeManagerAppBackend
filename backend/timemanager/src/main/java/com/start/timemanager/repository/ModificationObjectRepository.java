package com.start.timemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.start.timemanager.model.ModificationObject;

@Repository
public interface ModificationObjectRepository extends JpaRepository<ModificationObject, Long> {

}
