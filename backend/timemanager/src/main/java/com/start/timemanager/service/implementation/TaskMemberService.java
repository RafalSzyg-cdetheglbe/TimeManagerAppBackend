package com.start.timemanager.service.implementation;

import com.start.timemanager.dto.TaskMemberDTO;
import com.start.timemanager.error.DeletedExeption;
import com.start.timemanager.service.interfaces.ITaskMemberService;
import com.start.timemanager.model.*;
import com.start.timemanager.repository.TaskMemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskMemberService implements ITaskMemberService {

    private final TaskMemberRepository taskMemberRepository;

    public TaskMemberService(TaskMemberRepository taskMemberRepository) {
        this.taskMemberRepository = taskMemberRepository;

    }

    public List<TaskMember> getTaskMembers(Long task_id) {
        List<TaskMember> taskMembers = new ArrayList<TaskMember>();
        for (TaskMember taskMember : taskMemberRepository.findAll())
            if (taskMember.getTask().getId().equals(task_id))
                taskMembers.add(taskMember);

        return taskMembers;
    }


    public TaskMemberDTO addTaskMember(TaskMemberDTO taskMemberDTO) {
        TaskMember taskMember = new TaskMember();
        taskMember.setDeleted(false);
        taskMember.setNotificationDate(null);
        User user = new User();
        Task task = new Task();
        user.setId(taskMemberDTO.getUserId());
        task.setId(taskMemberDTO.getTaskId());
        taskMember.setTask(task);
        taskMember.setUser(user);
        this.taskMemberRepository.save(taskMember);

        taskMemberDTO.setId(taskMember.getId());
        return taskMemberDTO;

    }

    public Long deleteTaskMember(Long id) {
try {
    this.taskMemberRepository.deleteById(id);
}catch (Exception e){throw new DeletedExeption(e.getMessage());
}
return id;
    }

}
