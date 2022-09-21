package com.start.timemanager.service.implementation;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import com.start.timemanager.error.DeletedExeption;
import com.start.timemanager.error.ForbiddenException;
import com.start.timemanager.error.NotFoundException;
import com.start.timemanager.repository.TaskRepository;
import com.start.timemanager.repository.UserRepository;
import com.start.timemanager.service.interfaces.IActiveTimerService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.start.timemanager.dto.ActiveTimerDTO;
import com.start.timemanager.model.ActiveTimer;
import com.start.timemanager.model.Task;
import com.start.timemanager.model.User;
import com.start.timemanager.repository.ActiveTimerRepository;

@Service
@RequiredArgsConstructor
public class ActiveTimerService implements IActiveTimerService {
    private final ActiveTimerRepository activeTimerRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public ActiveTimerDTO getTimer(Long id, String username) {
        ActiveTimerDTO timer;
        User user = userRepository.findUserByEmail(username);
        try{
            if(!user.getUserRole().getName().toUpperCase().equals("ADMINISTRATOR"))
                timer = convertToDto(activeTimerRepository.findOneTimer(id, username));
            else
                timer = convertToDto(activeTimerRepository.findById(id).get());
        }catch(Exception exception){
            throw new NotFoundException("Timer not found");
        }
        
        if(!timer.isDeleted() || user.getUserRole().getName().equals("ADMINISTRATOR"))
            return timer;
        throw new DeletedExeption("Timer is deleted");
    }

    public List<ActiveTimerDTO> getAllTimers() {
        List<ActiveTimerDTO> activeTimerDTO = activeTimerRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
        if(!activeTimerDTO.isEmpty())
            return activeTimerDTO;
        throw new NotFoundException("Timer not Found");
    }

    public List<ActiveTimerDTO> getActiveTimers() {
        List<ActiveTimerDTO> timers = activeTimerRepository.findNotDelTimers()
                .stream().map(this::convertToDto).collect(Collectors.toList());
        if(!timers.isEmpty())
            return timers;
        throw new NotFoundException("Timer not Found");
        }

    public List<ActiveTimerDTO> getAllUserActiveTimers(String username) {
        List<ActiveTimerDTO> timers = activeTimerRepository.findUserActiveTimers(username)
                .stream().map(this::convertToDto).collect(Collectors.toList());
        if(!timers.isEmpty())
            return timers;
        throw new NotFoundException("Timer not Found");
    }

    public List<ActiveTimerDTO> getAllBucketActiveTimers(Long bucketId) {
        List<ActiveTimerDTO> activeTimerDTO = activeTimerRepository.findBucketActiveTimers(bucketId).stream().map(this::convertToDto).collect(Collectors.toList());
        if(!activeTimerDTO.isEmpty())
            return activeTimerDTO;
        throw new NotFoundException("Timer not Found");
        }

    public List<ActiveTimerDTO> getAllTaskActiveTimers(Long taskId) {
        List<ActiveTimerDTO> activeTimerDTO = activeTimerRepository.findTaskActiveTimers(taskId).stream().map(this::convertToDto).collect(Collectors.toList());
        if(!activeTimerDTO.isEmpty())
            return activeTimerDTO;
        throw new NotFoundException("Timer not Found");
    }

    public List<ActiveTimerDTO> getUserTimersInTask(Long taskId, String username) {
        List<ActiveTimerDTO> activeTimerDTO = activeTimerRepository.findUserTimersInTask(username, taskId)
                .stream().map(this::convertToDto).collect(Collectors.toList());
        if(!activeTimerDTO.isEmpty())
            return activeTimerDTO;
        throw new NotFoundException("Timer not Found");
    }

    public List<ActiveTimerDTO> getUserActiveTimersInTask(Long taskId, String username) {
        List<ActiveTimerDTO> activeTimerDTO = activeTimerRepository.findUserActiveTimersInTask(username, taskId)
                .stream().map(this::convertToDto).collect(Collectors.toList());
        if(!activeTimerDTO.isEmpty())
            return activeTimerDTO;
        new NotFoundException("Timer not Found");
        return null;
    }

    public ActiveTimerDTO createTimer(ActiveTimerDTO activeTimerDTO, String username) {

        ActiveTimer timer = new ActiveTimer();
        if(getUserActiveTimersInTask(activeTimerDTO.getTaskId(), username)==null){
            timer.setStartTime(null);
            timer.setStartPause(null);
            timer.setPaused(false);
            timer.setTimerRealizationTime(0);
            timer.setDeleted(false);
            User user = userRepository.findUserByEmail(username);
            Task task = new Task();
            task.setId(activeTimerDTO.getTaskId());
            timer.setTask(task);
            timer.setUser(user);
            System.out.println("Stworzono");
            activeTimerRepository.save(timer);
            startTimer(timer.getId(), username);
            return convertToDto(timer);
        }
        System.out.println("You have timer in this task");
        return null;
        
    }

    public ActiveTimerDTO startTimer(Long timerId, String username) {
        System.out.println("Wystartowano timer");
        LocalDateTime startTime = LocalDateTime.now();
        if(!activeTimerRepository.findById(timerId).isPresent())
            throw new NotFoundException("Not found ");
        ActiveTimer activeTimer = activeTimerRepository.findActiveTimer(timerId);
        if(activeTimer==null)
            throw new DeletedExeption("Deleted");
        if(username.equals(activeTimer.getUser().getEmail())){ 
            activeTimer.setStartTime(startTime);
            activeTimer.setPaused(false);

            activeTimerRepository.save(activeTimer);
            return convertToDto(activeTimer);
        }
        throw new ForbiddenException("forbidden");
    }

    public ActiveTimerDTO pauseTimer(Long timerId, String username) {
        System.out.println("Wstrzymano timer");
        LocalDateTime pauseTime = LocalDateTime.now();
        if(!activeTimerRepository.findById(timerId).isPresent())throw new NotFoundException("Not found ");
        ActiveTimer activeTimer = activeTimerRepository.findActiveTimer(timerId);
        if(activeTimer==null)
            throw new DeletedExeption("Deleted");
        if(username.equals(activeTimer.getUser().getEmail())){ 
            activeTimer.setStartPause(pauseTime);
            activeTimer.setPaused(true);
            if (activeTimer.getStartPause()!=null && activeTimer.getStartTime()!=null)
                activeTimer.setTimerRealizationTime(
                        activeTimer.getTimerRealizationTime() 
                        + (int) activeTimer.getStartPause().atZone(ZoneId.systemDefault()).toEpochSecond()
                        - (int) activeTimer.getStartTime().atZone(ZoneId.systemDefault()).toEpochSecond());

            activeTimerRepository.save(activeTimer);
            return convertToDto(activeTimer);
        }
        throw new ForbiddenException("forbidden");
    }

    public ActiveTimerDTO stopTimer(Long timerId, String username) {
        System.out.println("Zastopowano timer");
        LocalDateTime stopTime = LocalDateTime.now();
        if(!activeTimerRepository.findById(timerId).isPresent())throw new NotFoundException("Not found ");
        ActiveTimer activeTimer = activeTimerRepository.findActiveTimer(timerId);
        if(activeTimer==null)
            throw new DeletedExeption("Deleted");
        if(username.equals(activeTimer.getUser().getEmail())){ 
            activeTimer.setDeleted(true);
            if (activeTimer.getStartTime()!=null && !activeTimer.isPaused())
                activeTimer.setTimerRealizationTime(
                        activeTimer.getTimerRealizationTime() 
                        + (int) stopTime.atZone(ZoneId.systemDefault()).toEpochSecond()
                        - (int) activeTimer.getStartTime().atZone(ZoneId.systemDefault()).toEpochSecond());
            
            activeTimerRepository.save(activeTimer);
            if(activeTimer.getStartPause()==null && activeTimer.getStartTime()==null)
            return null;
            ActiveTimerDTO activeTimerDTO = convertToDto(activeTimer);
            Task task = taskRepository.findById(activeTimerDTO.getTaskId()).get();
            task.setTaskRealizationTime(activeTimerDTO.getRealizationTime());
            taskRepository.save(task);
            return activeTimerDTO;
        }
        throw new ForbiddenException("forbidden");
    }

    private ActiveTimerDTO convertToDto(ActiveTimer activeTimer){
        return new ActiveTimerDTO(
            activeTimer.getId(),
            activeTimer.getTask().getId(), 
            activeTimer.getUser().getId(),
            activeTimer.getStartTime(), 
            activeTimer.getStartPause(), 
            activeTimer.isPaused(), 
            activeTimer.getTimerRealizationTime(), 
            activeTimer.isDeleted());
    }
}
