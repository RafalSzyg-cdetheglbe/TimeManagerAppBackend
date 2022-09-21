package com.start.timemanager.service.interfaces;

import com.start.timemanager.dto.TaskMemberDTO;
import com.start.timemanager.model.TaskMember;

import java.util.List;

public interface ITaskMemberService {
    public List<TaskMember> getTaskMembers(Long task_id);

    public TaskMemberDTO addTaskMember(TaskMemberDTO taskMemberDTO);

    public Long deleteTaskMember(Long id);

}
