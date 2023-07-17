package de.backend.smarthome_backend.repository;

import de.backend.smarthome_backend.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for Device entities.
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    /**
     * Updates the status of a device.
     *
     * @param status    The new status of the device.
     * @param deviceID  The ID of the device to update.
     */
    @Transactional
    @Modifying
    @Query("update Device d set d.state = ?1 where d.deviceID = ?2")
    void updateStatus(Boolean status, Long deviceID);

    /**
     * Finds a device by its IP address.
     *
     * @param ip  The IP address of the device.
     * @return The device with the specified IP address.
     */
    @Query("SELECT d FROM Device d WHERE d.ip = ?1")
    Device findDeviceByIp(String ip);

    /**
     * Retrieves the status of a device.
     *
     * @param deviceID  The ID of the device.
     * @return The status of the device.
     */
    @Query("SELECT d.state FROM Device d WHERE d.deviceID = ?1")
    Boolean getStatusOfDevice(Long deviceID);

    @Query("SELECT d FROM Device d WHERE d.deviceID = ?1")
    Device getById(Long deviceID);
}
