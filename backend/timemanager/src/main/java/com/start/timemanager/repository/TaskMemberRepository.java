package com.start.timemanager.repository;

import com.start.timemanager.model.TaskMember;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMemberRepository extends JpaRepository<TaskMember, Long> {



   @Query("select t from TaskMember t where t.notificationDate is null")
  List<TaskMember>findByNullNotificationDate();

    final String taskQuery = "SELECT t.* FROM task_members t WHERE t.task_id=:id";

    final String userQuery = "SELECT t.* FROM task_members t WHERE t.user_id=:id";
    @Query(value = taskQuery, nativeQuery = true)
    List<TaskMember>findByTaskId(Long id);
    @Query(value = userQuery, nativeQuery = true)
    List<TaskMember> findByUserId(Long id);
}
