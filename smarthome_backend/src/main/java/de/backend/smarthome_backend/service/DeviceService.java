package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.Device;

import java.util.List;

/**
 * Service interface for the repository of devices.
 */
public interface DeviceService {

    Device getDeviceById(Long deviceId);

    /**
     * Saves a device.
     *
     * @param device The device to be saved.
     * @return The saved device.
     */
    Device saveDevice(Device device);

    /**
     * Retrieves a list of all devices.
     *
     * @return The list of devices.
     */
    List<Device> fetchDeviceList();

    /**
     * Updates a device.
     *
     * @param device   The updated device.
     * @param deviceID The ID of the device to be updated.
     */
    void updateDevice(Device device, Long deviceID);

    /**
     * Deletes a device by its ID.
     *
     * @param deviceID The ID of the device to be deleted.
     */
    void deleteDeviceById(Long deviceID);

    /**
     * Retrieves a device by its IP address.
     *
     * @param ip The IP address of the device.
     * @return The device with the specified IP address.
     */
    Device getByIp(String ip);

    /**
     * Changes the state of a device.
     *
     * @param state    The new state of the device.
     * @param deviceID The ID of the device to change the state.
     */
    void changeDeviceState(Boolean state, Long deviceID);
    /**
     * Retrieves the state of a device.
     *
     * @param deviceID The ID of the device.
     * @return The state of the device.
     */
    Boolean getDeviceState(Long deviceID);
    /**
     * Retrieves the basic electriciy usage of all device.
     *
     * @return The sum uf all usage.
     */
    Double getBasicUsageOfAllDevice();
    /**
     * Retrieves all DeviceID of Devices who Produce Energy .
     *
     * @return all DeviceID of Devices who Produce Energy.
     */
    List<Long> getAllDeviceProducer();
}
