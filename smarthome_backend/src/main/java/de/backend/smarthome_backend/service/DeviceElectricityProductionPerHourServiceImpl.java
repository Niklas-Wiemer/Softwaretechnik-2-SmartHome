package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.DeviceElectricityProductionPerHour;
import de.backend.smarthome_backend.repository.DeviceElectricityProductionPerHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link DeviceElectricityProductionPerHourService} interface for managing device electricity production statistics by day.
 */
@Service
public class DeviceElectricityProductionPerHourServiceImpl implements DeviceElectricityProductionPerHourService {

    private final DeviceElectricityProductionPerHourRepository electricityProductionPerHourRepository;

    /**
     * Constructs a new DeviceElectricityProductionStatsByDayServiceImpl with the specified repository.
     *
     * @param electricityProductionPerHourRepository The repository for device electricity production statistics by hour.
     */
    @Autowired
    public DeviceElectricityProductionPerHourServiceImpl(DeviceElectricityProductionPerHourRepository electricityProductionPerHourRepository) {
        this.electricityProductionPerHourRepository = electricityProductionPerHourRepository;
    }

    /**
     * Saves the device electricity production statistics for a specific hour.
     *
     * @param electricityProductionStats The device electricity production statistics to save.
     * @return The saved device electricity production statistics.
     */
    @Override
    public DeviceElectricityProductionPerHour saveElectricityProductionPerHour(DeviceElectricityProductionPerHour electricityProductionStats) {
        return electricityProductionPerHourRepository.save(electricityProductionStats);
    }

    /**
     * Retrieves a list of all device electricity production statistics by Hour.
     *
     * @return A list of device electricity production statistics by Hour.
     */
    @Override
    public List<DeviceElectricityProductionPerHour> fetchElectricityProductionPerHourList() {
        return electricityProductionPerHourRepository.findAll();
    }

    /**
     * Updates the device electricity production statistics for a specific Hour.
     *
     * @param electricityProductionStats The updated device electricity production statistics.
     * @param id                         The ID of the device electricity production statistics to update.
     */
    @Override
    public void updateElectricityProductionPerHour(DeviceElectricityProductionPerHour electricityProductionStats, Long id) {
        DeviceElectricityProductionPerHour electricityProductionStatsDB = electricityProductionPerHourRepository.findById(id).get();
        // Perform the necessary updates to the electricityProductionStatsDB object

        electricityProductionPerHourRepository.save(electricityProductionStatsDB);
    }

    /**
     * Deletes the device electricity production statistics for a specific Hour.
     *
     * @param id The ID of the device electricity production statistics to delete.
     */
    @Override
    public void deleteElectricityProductionPerHour(Long id) {
        electricityProductionPerHourRepository.deleteById(id);
    }
}
