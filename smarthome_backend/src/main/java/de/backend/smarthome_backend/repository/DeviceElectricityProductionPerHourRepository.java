package de.backend.smarthome_backend.repository;

import de.backend.smarthome_backend.entity.DeviceElectricityProductionPerHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceElectricityProductionPerHourRepository extends JpaRepository<DeviceElectricityProductionPerHour,Long > {
}
