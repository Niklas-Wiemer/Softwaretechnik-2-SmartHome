package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.Schedule;

import java.util.List;

/**
 * A service for managing schedules.
 */

public interface ScheduleService {

    /**
     * Saves a schedule.
     *
     * @param schedule The schedule to be saved.
     * @return The saved schedule.
     */
    Schedule saveSchedule(Schedule schedule);

    /**
     * Retrieves a list of schedules.
     *
     * @return The list of schedules.
     */
    List<Schedule> fetchScheduleList();

    /**
     * Updates a schedule with the specified ID.
     *
     * @param schedule The updated schedule.
     * @param id       The ID of the schedule to be updated.
     */
    void updateSchedule(Schedule schedule, Long id);

    /**
     * Deletes a schedule with the specified ID.
     *
     * @param id The ID of the schedule to be deleted.
     */
    void deleteSchedule(Long id);
}
