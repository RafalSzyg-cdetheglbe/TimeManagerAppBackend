package com.start.timemanager.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.start.timemanager.dto.BucketDTO;
import com.start.timemanager.error.ForbiddenException;

import com.start.timemanager.error.NotFoundException;
import com.start.timemanager.model.*;
import com.start.timemanager.repository.BucketMemberRepository;
import com.start.timemanager.repository.TaskRepository;
import com.start.timemanager.service.interfaces.IBucketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.start.timemanager.repository.BucketRepository;
import com.start.timemanager.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BucketService implements IBucketService {

    private final BucketRepository bucketRepository;
    private final ModificationHistoryService modificationHistoryService;
    private final BucketMemberService bucketMemberService;
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final BucketMemberRepository bucketMemberRepository;
    private final UserRepository userRepository;

    public List<BucketDTO> getBuckets(String email) {
        User user = userRepository.findUserByEmail(email);
        List<Bucket> buckets = bucketRepository.findBucketsForUser(user.getId());
        return convertBucketsIntoDTO(buckets);
    }

    public BucketDTO getBucket(Long id, String email) {
        List<BucketDTO> userBuckets = getBuckets(email);
        BucketDTO bucket = convertBucketIntoDTO(
            this.bucketRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found in database"))
        );
        
        for (BucketDTO userBucket : userBuckets) {
            if (userBucket.getId().equals(bucket.getId()) && !bucket.isDeleted())

                return bucket;
        }

        log.info("Nie masz dostępu do tego obiektu");
        throw new ForbiddenException("No Access");
    }

    @Transactional
    public BucketDTO addBucket(BucketDTO bucketDTO, String email) {
        LocalDateTime currentTime = LocalDateTime.now();
        User user = userRepository.findUserByEmail(email);
        Bucket bucket = new Bucket(
                bucketDTO.getName(),
                bucketDTO.getDescription(),
                user,
                0,
                currentTime,
                currentTime,
                bucketDTO.getMaxTasks(),
                false);
        this.bucketRepository.save(bucket);
        modificationHistoryService.saveHistory(user.getId(), 1L, 2L, bucket.getId(), currentTime);

        bucketDTO=convertBucketIntoDTO(bucket);
        return bucketDTO;
    }

    @Transactional
    public BucketDTO updateBucket(BucketDTO bucketDTO,Long id, String email) {
        User user = userRepository.findUserByEmail(email);
        Bucket bucket = this.bucketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not existing in database"));

        LocalDateTime time = LocalDateTime.now();
        bucket.setName(bucketDTO.getName());
        bucket.setDescription(bucketDTO.getDescription());
        bucket.setMaxTasks(bucket.getMaxTasks());
        bucket.setLastChange(time);
        // 1 parametr zmienić na uZytkownika z sesji
        bucketDTO=convertBucketIntoDTO(bucket);

        modificationHistoryService.saveHistory(user.getId(), 2L, 2L, bucket.getId(), time);
        return bucketDTO;
    }

    @Transactional
    public Long deleteBucket(Long id, String email) {
        Bucket bucket = this.bucketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found in database"));
        User user = userRepository.findUserByEmail(email);
System.out.println(user.getId().equals(bucket.getUser().getId()));
        if (user.getId().equals(bucket.getUser().getId())){
            System.out.println(!user.getId().equals(bucket.getUser().getId()));

            bucket.setDeleted(true);
            List<Task>tasks=taskRepository.findByBucketId(bucket.getId());
            List<BucketMember>bucketMembers=bucketMemberRepository.findByBucketId(bucket.getId());

            for(Task t:tasks){this.taskService.deleteTask(t.getId(),email);}

            for(BucketMember bm : bucketMembers){
                this.bucketMemberService.deleteBucketMember(bm.getId());
            }

            bucketRepository.save(bucket);
           modificationHistoryService.saveHistory(user.getId(), 3L, 2L, id, LocalDateTime.now());
            return bucket.getId();
        }else {
            System.out.println(!user.getId().equals(bucket.getUser().getId()));

            throw new ForbiddenException("Forbidden");
        }
    }

    public List<BucketDTO> convertBucketsIntoDTO(List<Bucket> buckets) {
        return buckets
                .stream()
                .map(this::convertBucketIntoDTO)
                .collect(Collectors.toList());
    }

    private BucketDTO convertBucketIntoDTO(Bucket bucket) {
        BucketDTO bucketDTO = new BucketDTO();
        bucketDTO.setId(bucket.getId());
        bucketDTO.setName(bucket.getName());
        bucketDTO.setDescription(bucket.getDescription());
        bucketDTO.setMaxTasks(bucket.getMaxTasks());
        bucketDTO.setActiveTasks(bucket.getActiveTasks());
        bucketDTO.setUserId(bucket.getUser().getId());
        bucketDTO.setLastChange(bucket.getLastChange());
        bucketDTO.setCreatedDate(bucket.getCreatedDate());
        return bucketDTO;
    }
}
