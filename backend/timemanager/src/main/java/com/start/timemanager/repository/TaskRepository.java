package com.start.timemanager.repository;

import com.start.timemanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    final String queryBucketTasks = "SELECT t.* FROM tasks t  WHERE t.bucket_id=:id";
    final String queryTaskMemberUser = "SELECT t.* FROM tasks t  WHERE t.user_id=:userId AND t.id=:id";
    @Query(value = queryBucketTasks, nativeQuery = true)
    List<Task> findByBucketId(Long id);
    @Query(value = queryTaskMemberUser, nativeQuery = true)
    Task findByUserAndTaskMember(Long id, Long userId);
}
