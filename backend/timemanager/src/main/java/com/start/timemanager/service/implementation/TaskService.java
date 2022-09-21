package com.start.timemanager.service.implementation;

import com.start.timemanager.dto.TaskDTO;
import com.start.timemanager.error.DeletedExeption;

import com.start.timemanager.error.ForbiddenException;
import com.start.timemanager.error.NotFoundException;
import com.start.timemanager.repository.*;
import com.start.timemanager.service.interfaces.ITaskService;
import com.start.timemanager.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private final TaskMemberRepository taskMemberRepository;
    private final ModificationHistoryService modificationHistoryService;
    private final BucketMemberService bucketMemberService;
    private final BucketRepository bucketRepository;
    private final ActiveTimerRepository activeTimerRepository;
    private final ActiveTimerService activeTimerService;
    private final UserRepository userRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    private final TaskMemberService taskMemberService;



    public TaskService(TaskRepository taskRepository, TaskMemberRepository taskMemberRepository,

                       ModificationHistoryService modificationHistoryService, BucketMemberService bucketMemberService,
                       BucketRepository bucketRepository, ActiveTimerRepository activeTimerRepository, ActiveTimerService activeTimerService, UserRepository userRepository, TaskMemberService taskMemberService) {
        this.taskRepository = taskRepository;
        this.taskMemberRepository = taskMemberRepository;
        this.modificationHistoryService = modificationHistoryService;
        this.bucketMemberService = bucketMemberService;
        this.bucketRepository = bucketRepository;

        this.activeTimerRepository = activeTimerRepository;
        this.activeTimerService = activeTimerService;
        this.userRepository = userRepository;
        this.taskMemberService = taskMemberService;
    }

    public Task getTask(Long id, String email) {
        boolean isMember=false;
        System.out.println(email);
        User user = this.userRepository.findUserByEmail(email);
        List<TaskMember>taskMembers=this.taskMemberRepository.findByTaskId(id);
        for(TaskMember tm:taskMembers){
            if (tm.getUser().getId().equals(user.getId())) {
                isMember = true;
                break;
            }
        }
        Task task=taskRepository.findById(id).orElseThrow(()->new NotFoundException("Not found in database"));
        if(!task.isDeleted() && task.getUser().getId().equals(user.getId())||isMember)
        return task;
        else {throw new ForbiddenException("Task was deleted or no access");
        }

    }

    public List<Task> getTasks(Long bucket_id) {
        List<Task> tasks = new ArrayList<Task>();
        for (Task task : taskRepository.findAll())
            if (!task.isDeleted() && task.getBucket().getId().equals(bucket_id))
                tasks.add(task);

        return tasks;
    }

    public List<Task> getDeletedTasks(Long bucket_id) {
        List<Task> tasks = new ArrayList<Task>();
        for (Task task : taskRepository.findAll())
            if (task.isDeleted() && task.getBucket().getId().equals(bucket_id))
                tasks.add(task);

        return tasks;
    }


    public TaskDTO addTask(TaskDTO taskDTO, String email) {
        User user = this.userRepository.findUserByEmail(email);

        Bucket bucket = bucketRepository.findById(taskDTO.getBucketId()).orElseThrow(() -> new NotFoundException("Not found in database"));
        if (bucket.getActiveTasks() >= bucket.getMaxTasks()) {throw new ForbiddenException("Bucket Overflow");
        }
            State state = new State();
            Priority priority = new Priority();

            bucket.setId(taskDTO.getBucketId());
            state.setId(taskDTO.getStateId());

            priority.setId(taskDTO.getPriorityId());
            LocalDateTime time = LocalDateTime.now();

            bucket.setActiveTasks(bucket.getActiveTasks() + 1);
            Task task = new Task(
                    taskDTO.getName(),
                    taskDTO.getDescription(),
                    user,
                    bucket,
                    state,
                    time,
                    taskDTO.getDeadline(),
                    0,
                    priority,
                    time,
                    taskDTO.getEstimatedEndTime(),
                    false);
            taskRepository.save(task);

           modificationHistoryService.saveHistory(user.getId(), 1L, 3L, task.getId(), time);
            taskDTO=convertTaskIntoDTO(task);
            return taskDTO;


    }



    @Transactional
    public Long deleteTask(Long id,String email) {
        boolean isBucketAuthor=false;
        User user=this.userRepository.findUserByEmail(email);
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found in database"));
        List<Bucket>buckets = bucketRepository.findBucketsForUser(user.getId());
        for(Bucket b:buckets){
            if (b.getUser().getId().equals(user.getId())) {
                isBucketAuthor = true;
                break;
            }
        }

        if(!task.getUser().getId().equals(user.getId())||!isBucketAuthor)throw new ForbiddenException("Editor is not an author");

        Bucket bucket = bucketRepository.findById(task.getBucket().getId())
                .orElseThrow(() -> new NotFoundException("Task not found in database"));
        bucket.setActiveTasks(bucket.getActiveTasks() - 1);
        task.setDeleted(true);

        List<ActiveTimer> activeTimers=this.activeTimerRepository.findTaskActiveTimers(task.getId());
        List<TaskMember>taskMembers=this.taskMemberRepository.findByTaskId(task.getId());

        for(ActiveTimer at:activeTimers){
            this.activeTimerService.stopTimer(at.getId(),email);
        }

        for(TaskMember tm:taskMembers){
            this.taskMemberService.deleteTaskMember(tm.getId());
        }
        bucketRepository.save(bucket);
        modificationHistoryService.saveHistory(1L, 3L, 3L, id, LocalDateTime.now());
        return task.getId();
    }

    @Transactional
    public TaskDTO editTask(TaskDTO taskDTO, Long id, String email) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found in database"));
        User user=this.userRepository.findUserByEmail(email);
        if(!task.getUser().getId().equals(user.getId()))throw new ForbiddenException("Editor is not an author");
        Priority priority = priorityRepository.findById(taskDTO.getPriorityId())
                .orElseThrow(() -> new NotFoundException("Priority not found in database"));
        task.setPriority(priority);
        task.setName(taskDTO.getName());
        task.setDeadline(taskDTO.getDeadline());
        task.setDescription(taskDTO.getDescription());
        task.setEstimatedEndTime(taskDTO.getEstimatedEndTime());
        task.setLastChange(LocalDateTime.now());
        taskRepository.save(task);
        modificationHistoryService.saveHistory(1L, 2L, 3L, id, LocalDateTime.now());

       taskDTO=convertTaskIntoDTO(task);
        return taskDTO;
    }

    public List<Task> getOutsideTasks(String email) {
        List<Task> tasks = new ArrayList<>();
        User user= this.userRepository.findUserByEmail(email);
        for (TaskMember taskMember : taskMemberRepository.findByUserId(user.getId())) {
            if (!bucketMemberService.checkBucketMember(user.getId(), taskMember.getTask().getBucket().getId())
                    && !taskMember.getTask().isDeleted())
                tasks.add(taskMember.getTask());
        }
        return tasks;
    }

    public List<TaskDTO> getAllTasks() {

        return ((List<Task>) taskRepository
                .findAll())
                .stream()
                .map(this::convertTaskIntoDTO)
                .collect(Collectors.toList());
    }


    private TaskDTO convertTaskIntoDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setUserId(task.getUser().getId());
        taskDTO.setPriorityId(task.getPriority().getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDeadline(task.getDeadline());
        taskDTO.setBucketId(task.getBucket().getId());
        taskDTO.setStateId(task.getState().getId());
        taskDTO.setId(task.getId());
        taskDTO.setDeleted(task.isDeleted());
        taskDTO.setCreaDate(task.getCreatedDate());
        taskDTO.setRealizationTime((long) task.getTaskRealizationTime());
        taskDTO.setEstimatedEndTime(task.getEstimatedEndTime());
        return taskDTO;
    }

    // private Task convertDtoIntoTask(TaskDTO taskDTO, Long id) {

    //     Task task = taskRepository.findById(id).orElseThrow(RuntimeException::new);
    //     Priority priority = priorityRepository.findById(taskDTO.getPriorityId())
    //             .orElseThrow(RuntimeException::new);
    //     task.setPriority(priority);

    //     task.setName(taskDTO.getName());
    //     task.setdescription(taskDTO.getDescription());
    //     task.setEstimatedEndTime(taskDTO.getEstimatedEndTime());
    //     task.setDeadline(taskDTO.getDeadline());
    //     task.setBucket(task.getBucket());
    //     task.setState(task.getState());

    //     return task;
    // }



}
