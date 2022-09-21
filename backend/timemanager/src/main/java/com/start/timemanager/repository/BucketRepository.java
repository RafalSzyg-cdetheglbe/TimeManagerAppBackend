package com.start.timemanager.repository;

import java.util.List;

import com.start.timemanager.model.TaskMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.start.timemanager.model.Bucket;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    final String queryAuthor = "SELECT b.* FROM buckets b WHERE b.user_id=:userId AND b.is_deleted IS false";


    List<Bucket> findBucketByUser(Long id);

    final String query = "SELECT b.* FROM buckets b WHERE b.user_id=:userId AND b.is_deleted IS false"
            + " UNION " +
            "SELECT b.* FROM buckets b inner join bucket_members bm ON bm.user_id=:userId AND b.is_deleted IS false AND b.id=bm.bucket_id"
            + " ORDER BY last_change DESC";

    final String bucketQuery = "SELECT t.* FROM task_members t WHERE t.task_id=:id";

    @Query(value = query, nativeQuery = true)
    List<Bucket> findBucketsForUser(Long userId);
    @Query(value = queryAuthor, nativeQuery = true)
    List<Bucket> findAuthorBucketsForUser(Long userId);
    @Query(value = bucketQuery, nativeQuery = true)
    List<TaskMember>findByBucketId(Long id);
}
