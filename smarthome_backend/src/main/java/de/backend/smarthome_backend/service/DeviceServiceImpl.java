package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.Device;
import de.backend.smarthome_backend.enums.DevicePurpose;
import de.backend.smarthome_backend.repository.DeviceRepository;
import de.backend.smarthome_backend.simulation.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the DeviceService.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    /**
     * Retrieves a device by its ID.
     *
     * @param deviceId The ID of the device.
     * @return The device with the specified ID.
     */
    @Override
    public Device getDeviceById(Long deviceId) {
        return deviceRepository.getById(deviceId);
    }

    /**
     * Saves a device.
     *
     * @param device The device to be saved.
     * @return The saved device.
     */
    @Override
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    /**
     * Retrieves a list of all devices.
     *
     * @return The list of devices.
     */
    @Override
    public List<Device> fetchDeviceList() {
        return deviceRepository.findAll();
    }

    /**
     * Updates a device.
     *
     * @param device   The updated device.
     * @param deviceID The ID of the device to be updated.
     */
    @Override
    public void updateDevice(Device device, Long deviceID) {
        Device deviceDB = deviceRepository.findById(deviceID).orElse(null);

        if (deviceDB != null) {
            deviceDB.setDeviceID(device.getDeviceID());
            deviceDB.setIp(device.getIp());
            deviceDB.setDeviceName(device.getDeviceName());
            deviceDB.setRoom(device.getRoom());
            deviceDB.setDevicePurpose(device.getDevicePurpose());
            deviceDB.setDeviceType(device.getDeviceType());
            deviceDB.setState(device.getState());
            deviceDB.setElectricityBasicUsage(device.getElectricityBasicUsage());
            deviceDB.setRuntime(device.getRuntime());
        }

        // Save the updated device
        deviceRepository.save(device);
    }

    /**
     * Deletes a device by its ID.
     *
     * @param deviceID The ID of the device to be deleted.
     */
    @Override
    public void deleteDeviceById(Long deviceID) {
        deviceRepository.deleteById(deviceID);
    }

    /**
     * Retrieves a device by its IP address.
     *
     * @param ip The IP address of the device.
     * @return The device with the specified IP address.
     */
    @Override
    public Device getByIp(String ip) {
        return deviceRepository.findDeviceByIp(ip);
    }

    /**
     * Changes the state of a device.
     *
     * @param state    The new state of the device.
     * @param deviceID The ID of the device.
     */
    @Override
    public void changeDeviceState(Boolean state, Long deviceID) {
        deviceRepository.updateStatus(state, deviceID);
    }

    /**
     * Retrieves the state of a device.
     *
     * @param deviceID The ID of the device.
     * @return The state of the device.
     */
    @Override
    public Boolean getDeviceState(Long deviceID) {
        return deviceRepository.getStatusOfDevice(deviceID);
    }
    /**.
     *
     * @return The sum of all basic electricity .
     */
    @Override
    public Double getBasicUsageOfAllDevice() {
        List<Device> deviceList = fetchDeviceList();
        double sum = 0.0;
        assert deviceList != null;
        for (Device d : deviceList) {
            if(d.getState() == Boolean.TRUE && d.getDevicePurpose() == DevicePurpose.CONSUMER)
            sum = sum + d.getElectricityBasicUsage();
            if(d.getState() == Boolean.TRUE && d.getDevicePurpose() == DevicePurpose.PRODUCER)
            sum = sum - d.getElectricityBasicProduction();
        }
        return sum + SimulationService.calculateGrundlast();
    }
    /**.
     *
     * @return All DeviceId who Produce Energy  .
     */
    @Override
    public List<Long> getAllDeviceProducer() {
        List<Long> returnList = new ArrayList<>();
        for (Device d: fetchDeviceList()) {
            if(d.getDevicePurpose() == DevicePurpose.PRODUCER)
                returnList.add(d.getDeviceID());
        }
        return returnList;
    }
}
