package de.backend.smarthome_backend.controller;

import de.backend.smarthome_backend.StaticSmartHome;
import de.backend.smarthome_backend.entity.Device;
import de.backend.smarthome_backend.entity.DeviceRunStats;
import de.backend.smarthome_backend.service.DeviceRunStatsService;
import de.backend.smarthome_backend.service.DeviceService;
import de.backend.smarthome_backend.simulation.SimulationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
/**
 * Controller for the Rest API and communication over the Web.
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService deviceService;
    private final DeviceRunStatsService deviceRunStatsService;
    private final SimulationService simulationService;

    @Autowired
    public DeviceController(DeviceService deviceService, DeviceRunStatsService deviceRunStatsService, SimulationService simulationService) {
        this.deviceService = deviceService;
        this.deviceRunStatsService = deviceRunStatsService;
        this.simulationService = simulationService;
    }

    /**
     * Saves a new device entry.
     *
     * @param device The device to be saved.
     * @return Copy of the newly added device entry.
     */
    @PostMapping()
    public ResponseEntity<Device> saveDevice(@Valid @RequestBody Device device) {
        Device newEntry = deviceService.saveDevice(device);
        simulationService.initDevice(device.getDeviceID());
        return ResponseEntity.ok(newEntry);
    }

    /**
     * Fetches a list of all device entries.
     *
     * @return List of all device entries.
     */
    @GetMapping()
    public ResponseEntity<List<Device>> fetchDeviceList() {
        List<Device> deviceList = deviceService.fetchDeviceList();
        return ResponseEntity.ok(deviceList);
    }

    /**
     * Updates a device entry.
     *
     * @param device   The updated device information.
     * @param deviceID The ID of the device to be updated.
     * @return Copy of the updated version of the device entry.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDevice(@RequestBody Device device, @PathVariable("id") Long deviceID) {
        try {
            deviceService.updateDevice(device, deviceID);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a device entry by ID.
     *
     * @param deviceID The ID of the device to be deleted.
     * @return Confirmation string.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceById(@PathVariable("id") Long deviceID) {
        deviceService.deleteDeviceById(deviceID);
        return ResponseEntity.ok().build();
    }

    /**
     * Gets a device by its IP address.
     *
     * @param ip The IP address of the device.
     * @return The device with the specified IP address.
     */
    @GetMapping("/getByIp/{ip}")
    public ResponseEntity<Device> getDevice(@PathVariable("ip") String ip) {
        Device device = deviceService.getByIp(ip);
        return ResponseEntity.ok(device);
    }

    /**
     * Sends a request to the Smart Grid to check if a device is turned on.
     * If the response status is OK, it changes the device state to "On".
     *
     * @param deviceID The ID of the device to check and update the state for.
     * @return Response entity with the status of the request.
     */
    @GetMapping("/checkDeviceOn/{deviceId}")
    public ResponseEntity<Void> checkDeviceOn(@PathVariable("deviceId") Long deviceID) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(String.format("%s/%s", "https://icecreamparty.de/api/smartgrid/smart-home/device/", StaticSmartHome.smartHomeId), String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            deviceService.changeDeviceState(true, deviceID);
            deviceRunStatsService.saveDeviceRunStats(simulationService.createDeviceRunStats(deviceID));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Turns a device on based on the provided device ID.
     *
     * @param deviceID The ID of the device to turn on.
     * @return Response entity with the status of the request.
     */
    @PostMapping("/turnDeviceOn/{deviceId}")
    public ResponseEntity<Void> turnDeviceOn(@PathVariable("deviceId") Long deviceID) {
        if (deviceService.getDeviceById(deviceID) != null) {
            deviceService.changeDeviceState(true, deviceID);
            deviceRunStatsService.saveDeviceRunStats(simulationService.createDeviceRunStats(deviceID));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Turns a device off based on the provided device ID.
     *
     * @param deviceID The ID of the device to turn off.
     * @return Response entity with the status of the request.
     */
    @PostMapping("/turnDeviceOff/{deviceId}")
    public ResponseEntity<Void> turnDeviceOff(@PathVariable("deviceId") Long deviceID) {
        if (deviceService.getDeviceById(deviceID) != null) {
            deviceService.changeDeviceState(false, deviceID);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Gets the state of a device based on the provided device ID.
     *
     * @param deviceID The ID of the device to get the state for.
     * @return The state of the device.
     */
    @GetMapping("/getState/{id}")
    public ResponseEntity<Boolean> getState(@PathVariable("id") Long deviceID) {
        Boolean state = deviceService.getDeviceState(deviceID);
        return ResponseEntity.ok(state);
    }
    /**
     * Gets the Electicity Usage of all Device
     *
     * @return The Electicity Usage of all Device .
     */
    @GetMapping("/getBasicUsageOfAllDevice")
    public ResponseEntity<Double> getBasicUsageOfAllDevice() {
       Double sum = deviceService.getBasicUsageOfAllDevice();
        return ResponseEntity.ok(sum);
    }
}

