package com.start.timemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.start.timemanager.model.ActiveTimer;

@Repository
public interface ActiveTimerRepository extends JpaRepository<ActiveTimer, Long> {
    final String queryNotDelTimers = "SELECT actTim.* FROM active_timers actTim WHERE actTim.is_deleted IS false";
    @Query(value = queryNotDelTimers, nativeQuery = true)
    List<ActiveTimer> findNotDelTimers();

    final String queryBucketActiveTimers = "SELECT actTim.* FROM active_timers actTim  JOIN tasks on actTim.task_id = tasks.id and tasks.bucket_id=:bucketId and actTim.is_deleted IS false";
    @Query(value = queryBucketActiveTimers, nativeQuery = true)
    List<ActiveTimer> findBucketActiveTimers(Long bucketId);

    final String queryTaskTimers = "SELECT actTim.* FROM active_timers actTim  WHERE actTim.task_id=:taskId";
    @Query(value = queryTaskTimers, nativeQuery = true)
    List<ActiveTimer> findTaskTimers(Long taskId);

    final String queryTaskActiveTimers = "SELECT actTim.* FROM active_timers actTim  WHERE actTim.task_id=:taskId AND actTim.is_deleted IS false";
    @Query(value = queryTaskActiveTimers, nativeQuery = true)
    List<ActiveTimer> findTaskActiveTimers(Long taskId);

    final String queryUserTimersInTask = "SELECT actTim.* FROM active_timers actTim inner join users on actTim.user_id=users.id and actTim.task_id=:taskId AND users.email=:username";
    @Query(value = queryUserTimersInTask, nativeQuery = true)
    List<ActiveTimer> findUserTimersInTask(String username, Long taskId);

    final String queryUserActiveTimersInTask = "SELECT actTim.* FROM active_timers actTim inner join users on actTim.user_id=users.id and actTim.task_id=:taskId AND users.email=:username AND actTim.is_deleted is false";
    @Query(value = queryUserActiveTimersInTask, nativeQuery = true)
    List<ActiveTimer> findUserActiveTimersInTask(String username, Long taskId);

    final String queryFindActiveTimer = "SELECT actTim.* FROM active_timers actTim  WHERE actTim.id=:id and actTim.is_deleted is false";
    @Query(value = queryFindActiveTimer, nativeQuery = true)
    ActiveTimer findActiveTimer(Long id);

    final String queryFindOneTimer = "select active_timers.* from active_timers inner join users on users.id=active_timers.user_id and users.email=:username and active_timers.id=:id";
    @Query(value = queryFindOneTimer, nativeQuery = true)
    ActiveTimer findOneTimer(Long id, String username);

    final String queryFindUserActiveTimers = "select active_timers.* from active_timers inner join users on users.id=active_timers.user_id and users.email=:username and active_timers.is_deleted is false";
    @Query(value = queryFindUserActiveTimers, nativeQuery = true)
    List<ActiveTimer> findUserActiveTimers(String username);
}
