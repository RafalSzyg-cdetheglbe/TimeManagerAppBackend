package com.start.timemanager.service.interfaces;

import com.start.timemanager.dto.TaskDTO;
import com.start.timemanager.model.*;

import java.security.Principal;
import java.util.List;

public interface ITaskService {
    public Task getTask(Long id, String email);

    public List<Task> getTasks(Long bucket_id);
    public List<TaskDTO> getAllTasks();
    public List<Task> getDeletedTasks(Long bucket_id);

    public TaskDTO addTask(TaskDTO taskDTO, String email);

    public Long deleteTask(Long id,String email);

       public TaskDTO editTask(TaskDTO taskDTO, Long id, String email);

    public List<Task> getOutsideTasks(String email);
}
