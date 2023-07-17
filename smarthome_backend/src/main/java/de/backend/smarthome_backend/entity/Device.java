package de.backend.smarthome_backend.entity;

import de.backend.smarthome_backend.enums.DevicePurpose;
import de.backend.smarthome_backend.enums.DeviceType;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a device.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    /**
     * Unique identifier for the device.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "device_id", nullable = false)
    private Long deviceID;

    /**
     * IP address of the device.
     */
    @Column(name = "ip")
    private String ip;

    /**
     * Name of the device.
     */
    @Column(name = "device_name")
    private String deviceName;

    /**
     * Room where the device is located.
     */
    @Column(name = "room")
    private String room;

    /**
     * Purpose of the device (e.g., CONSUMER, PRODUCER).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "device_purpose")
    private DevicePurpose devicePurpose;

    /**
     * Type of the device (e.g., thermostat, bulb, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "device_type")
    private DeviceType deviceType;

    /**
     * Current state of the device (On/Off).
     */
    @Column(name = "state")
    private Boolean state;

    /**
     * Basic electricity usage of the device.
     */
    @Column(name = "electricity_basic_usage")
    private Double electricityBasicUsage;

    /**
     * Basic Electricity production of the device.
     */
    @Column(name = "electricity_basic_production")
    private Double electricityBasicProduction;

    /**
     * Total runtime of the device.
     */
    @Column(name = "runtime")
    private Double runtime;

    // Getters and Setters

    /**
     * Get the unique identifier for the device.
     *
     * @return The device ID.
     */
    public Long getDeviceID() {
        return deviceID;
    }

    /**
     * Set the unique identifier for the device.
     *
     * @param deviceID The device ID to set.
     */
    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }

    /**
     * Get the IP address of the device.
     *
     * @return The IP address.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set the IP address of the device.
     *
     * @param ip The IP address to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Get the name of the device.
     *
     * @return The device name.
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Set the name of the device.
     *
     * @param deviceName The device name to set.
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * Get the room where the device is located.
     *
     * @return The room.
     */
    public String getRoom() {
        return room;
    }

    /**
     * Set the room where the device is located.
     *
     * @param room The room to set.
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Get the purpose of the device.
     *
     * @return The device purpose.
     */
    public DevicePurpose getDevicePurpose() {
        return devicePurpose;
    }

    /**
     * Set the purpose of the device.
     *
     * @param devicePurpose The device purpose to set.
     */
    public void setDevicePurpose(DevicePurpose devicePurpose) {
        this.devicePurpose = devicePurpose;
    }

    /**
     * Get the type of the device.
     *
     * @return The device type.
     */
    public DeviceType getDeviceType() {
        return deviceType;
    }

    /**
     * Set the type of the device.
     *
     * @param deviceType The device type to set.
     */
    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Get the current state of the device.
     *
     * @return The device state.
     */
    public Boolean getState() {
        return state;
    }

    /**
     * Set the current state of the device.
     *
     * @param state The device state to set.
     */
    public void setState(Boolean state) {
        this.state = state;
    }

    /**
     * Get the basic electricity usage of the device.
     *
     * @return The electricity basic usage.
     */
    public Double getElectricityBasicUsage() {
        return electricityBasicUsage;
    }

    /**
     * Set the basic electricity usage of the device.
     *
     * @param electricityBasicUsage The electricity basic usage to set.
     */
    public void setElectricityBasicUsage(Double electricityBasicUsage) {
        this.electricityBasicUsage = electricityBasicUsage;
    }
    /**
     * Get the total runtime of the device.
     *
     * @return The runtime.
     */
    public Double getRuntime() {
        return runtime;
    }

    /**
     * Set the total runtime of the device.
     *
     * @param runtime The runtime to set.
     */
    public void setRuntime(Double runtime) {
        this.runtime = runtime;
    }
    /**
     * Get the Basic Electricity Production of the Device.
     *
     * @return The electricityBasicProduction.
     */
    public Double getElectricityBasicProduction() {
        return electricityBasicProduction;
    }
    /**
     * Set the Basic Electricity Production of the device.
     *
     * @param electricityBasicProduction The Electricity Production of the Device.
     */
    public void setElectricityBasicProduction(Double electricityBasicProduction) {
        this.electricityBasicProduction = electricityBasicProduction;
    }

}
