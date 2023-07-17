package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.Schedule;
import de.backend.smarthome_backend.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * Implementation of the ScheduleService interface.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * Constructs a new ScheduleServiceImpl with the given ScheduleRepository.
     *
     * @param scheduleRepository The ScheduleRepository to be used.
     */
    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * Saves a schedule.
     *
     * @param schedule The schedule to be saved.
     * @return The saved schedule.
     */
    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    /**
     * Retrieves a list of schedules.
     *
     * @return The list of schedules.
     */
    @Override
    public List<Schedule> fetchScheduleList() {
        return scheduleRepository.findAll();
    }

    /**
     * Updates a schedule with the specified ID.
     *
     * @param schedule The updated schedule.
     * @param id       The ID of the schedule to be updated.
     */
    @Override
    public void updateSchedule(Schedule schedule, Long id) {
        Schedule scheduleDB = scheduleRepository.findById(id).orElse(null);
        if (scheduleDB != null) {
            scheduleDB.setScheduleBeginn(schedule.getScheduleBeginn());
            scheduleDB.setScheduleEnd(schedule.getScheduleEnd());
            scheduleRepository.save(scheduleDB);
        }
    }

    /**
     * Deletes a schedule with the specified ID.
     *
     * @param id The ID of the schedule to be deleted.
     */
    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}

