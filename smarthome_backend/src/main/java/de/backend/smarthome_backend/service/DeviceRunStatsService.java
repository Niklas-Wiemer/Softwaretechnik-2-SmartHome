package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.DeviceRunStats;

import java.util.List;

/**
 * Service interface for managing device usage statistics by day.
 */
public interface DeviceRunStatsService {

    /**
     * Saves the device usage statistics for a specific day.
     *
     * @param deviceRunStats The device usage statistics to save.
     * @return The saved device usage statistics.
     */
    DeviceRunStats saveDeviceRunStats(DeviceRunStats deviceRunStats);

    /**
     * Retrieves a list of all device usage statistics by day.
     *
     * @return A list of device usage statistics by day.
     */
    List<DeviceRunStats> fetchDeviceRunList();

    /**
     * Updates the device usage statistics for a specific day.
     *
     * @param deviceRunStats The updated device usage statistics.
     * @param id                    The ID of the device usage statistics to update.
     */
    void updateDeviceRunStats(DeviceRunStats deviceRunStats, Long id);

    /**
     * Deletes the device usage statistics for a specific day.
     *
     * @param id The ID of the device usage statistics to delete.
     */
    void deleteDeviceRunStats(Long id);
}
