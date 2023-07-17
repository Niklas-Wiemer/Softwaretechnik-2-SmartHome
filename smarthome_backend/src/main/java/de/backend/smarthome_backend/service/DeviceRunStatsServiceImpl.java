package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.DeviceRunStats;
import de.backend.smarthome_backend.repository.DeviceRunStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link DeviceRunStatsService} interface for managing device usage statistics by day.
 */
@Service
public class DeviceRunStatsServiceImpl implements DeviceRunStatsService {

    private final DeviceRunStatsRepository deviceRunStatsRepository;

    @Autowired
    public DeviceRunStatsServiceImpl(DeviceRunStatsRepository deviceRunStatsRepository) {
        this.deviceRunStatsRepository = deviceRunStatsRepository;
    }

    /**
     * Saves the device usage statistics for a specific run.
     *
     * @param deviceRunStats The device usage statistics to save.
     * @return The saved device usage statistics.
     */
    @Override
    public DeviceRunStats saveDeviceRunStats(DeviceRunStats deviceRunStats) {
        return deviceRunStatsRepository.save(deviceRunStats);
    }

    /**
     * Retrieves a list of all device usage statistics by run.
     *
     * @return A list of device usage statistics by run.
     */
    @Override
    public List<DeviceRunStats> fetchDeviceRunList() {
        return deviceRunStatsRepository.findAll();
    }

    /**
     * Updates the device usage statistics for a specific run.
     *
     * @param deviceRunStats The updated device usage statistics.
     * @param id                    The ID of the device usage statistics to update.
     */
    @Override
    public void updateDeviceRunStats(DeviceRunStats deviceRunStats, Long id) {
        DeviceRunStats deviceRunStatsDB = deviceRunStatsRepository.findById(id).get();
        deviceRunStatsRepository.save(deviceRunStatsDB);
    }

    /**
     * Deletes the device usage statistics for a specific run.
     *
     * @param id The ID of the device usage statistics to delete.
     */
    @Override
    public void deleteDeviceRunStats(Long id) {
        deviceRunStatsRepository.deleteById(id);
    }
}
