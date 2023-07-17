package de.backend.smarthome_backend.repository;

import de.backend.smarthome_backend.entity.DeviceRunStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRunStatsRepository extends JpaRepository<DeviceRunStats, Long> {
}