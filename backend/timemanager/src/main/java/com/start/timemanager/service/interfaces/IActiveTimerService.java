package com.start.timemanager.service.interfaces;

import com.start.timemanager.dto.ActiveTimerDTO;

import java.util.List;

public interface IActiveTimerService {
    public ActiveTimerDTO getTimer(Long id, String username);
    public List<ActiveTimerDTO> getAllTimers();
    public List<ActiveTimerDTO> getActiveTimers();
    public List<ActiveTimerDTO> getAllUserActiveTimers(String username);
    public List<ActiveTimerDTO> getAllBucketActiveTimers(Long bucketId);
    public List<ActiveTimerDTO> getAllTaskActiveTimers(Long taskId);
    public List<ActiveTimerDTO> getUserTimersInTask(Long userId, String username);
    public List<ActiveTimerDTO> getUserActiveTimersInTask(Long userId, String username);

    public ActiveTimerDTO createTimer(ActiveTimerDTO activeTimerDTO, String username);
    public ActiveTimerDTO startTimer(Long timerId, String username);
    public ActiveTimerDTO pauseTimer(Long timerId, String username);
    public ActiveTimerDTO stopTimer(Long timerId, String username);
}
