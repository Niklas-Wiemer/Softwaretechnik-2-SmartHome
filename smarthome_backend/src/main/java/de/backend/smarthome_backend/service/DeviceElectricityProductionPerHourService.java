package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.DeviceElectricityProductionPerHour;

import java.util.List;

/**
 * Service interface for managing device electricity production statistics by hour.
 */

public interface DeviceElectricityProductionPerHourService {

    /**
     * Saves the device electricity production statistics for a specific hour.
     *
     * @param electricityProductionPerHour The device electricity production statistics to save.
     * @return The saved device electricity production statistics.
     */
    DeviceElectricityProductionPerHour saveElectricityProductionPerHour(DeviceElectricityProductionPerHour electricityProductionPerHour);

    /**
     * Retrieves a list of all device electricity production statistics by hour.
     *
     * @return A list of device electricity production statistics by hour.
     */
    List<DeviceElectricityProductionPerHour> fetchElectricityProductionPerHourList();

    /**
     * Updates the device electricity production statistics for a specific day.
     *
     * @param electricityProductionPerHour The updated device electricity production statistics.
     * @param id                         The ID of the device electricity production statistics to update.
     */
    void updateElectricityProductionPerHour(DeviceElectricityProductionPerHour electricityProductionPerHour, Long id);

    /**
     * Deletes the device electricity production statistics for a specific Hour.
     *
     * @param id The ID of the device electricity production statistics to delete.
     */
    void deleteElectricityProductionPerHour(Long id);
}

