package de.backend.smarthome_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * Represents a schedule for a device.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    /**
     * The unique ID of the schedule.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleID;

    /**
     * The device associated with the schedule.
     */
    @ManyToOne()
    @JoinColumn(name = "device_id")
    private Device device;
    /**
     * The Smart Home Id
     */
    @JoinColumn(name = "smart_home_id")
    private Long smartHomeId;
    /**
     * The RunTime of the Schedule
     */
    @JoinColumn(name = "runtime")
    private Duration runtime;

    /**
     * The beginning time of the schedule (hh.mm.ss,dd.mm.yy).
     */
    @Column(name = "schedule_beginn")
    private String scheduleBeginn;

    /**
     * The end time of the schedule format(hh.mm.ss,dd.mm.yy)
     */
    @Column(name = "schedule_end")
    private String scheduleEnd;

    /**
     * The desiredPrice Price
     */
    @Column(name = "desired_price")
    private String desiredPrice;

    /**
     * The canExceedPrice Price
     */
    @Column(name = "can_exceed_price")
    private Boolean canExceedPrice;

    /**
     * The shouldStopDevice
     */
    @Column(name = "should_stop_device")
    private Boolean shouldStopDevice;
    /**
     * Retrieves the unique ID of the schedule.
     *
     * @return The ID of the schedule.
     */
    public Long getScheduleID() {
        return scheduleID;
    }

    /**
     * Sets the unique ID of the schedule.
     *
     * @param scheduleID The ID of the schedule.
     */
    public void setScheduleID(Long scheduleID) {
        this.scheduleID = scheduleID;
    }

    /**
     * Retrieves the device associated with the schedule.
     *
     * @return The device object.
     */
    public Device getDevice() {
        return device;
    }

    /**
     * Sets the device associated with the schedule.
     *
     * @param device The device object.
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    /**
     * Retrieves the beginning time of the schedule.
     *
     * @return The beginning time of the schedule.
     */
    public String getScheduleBeginn() {
        return scheduleBeginn;
    }

    /**
     * Sets the beginning time of the schedule.
     *
     * @param scheduleBeginn The beginning time of the schedule.
     */
    public void setScheduleBeginn(String scheduleBeginn) {
        this.scheduleBeginn = scheduleBeginn;
    }

    /**
     * Retrieves the end time of the schedule.
     *
     * @return The end time of the schedule.
     */
    public String getScheduleEnd() {
        return scheduleEnd;
    }

    /**
     * Sets the end time of the schedule.
     *
     * @param scheduleEnd The end time of the schedule.
     */
    public void setScheduleEnd(String scheduleEnd) {
        this.scheduleEnd = scheduleEnd;
    }

    /**
     * Retrieves the smart home ID.
     *
     * @return The smart home ID.
     */
    public Long getSmartHomeId() {
        return smartHomeId;
    }

    /**
     * Sets the smart home ID.
     *
     * @param smartHomeId The smart home ID to set.
     */
    public void setSmartHomeId(Long smartHomeId) {
        this.smartHomeId = smartHomeId;
    }

    /**
     * Retrieves the runtime.
     *
     * @return The runtime.
     */
    public Duration getRuntime() {
        return runtime;
    }

    /**
     * Sets the runtime.
     *
     * @param runtime The runtime to set.
     */
    public void setRuntime(Duration runtime) {
        this.runtime = runtime;
    }

    /**
     * Retrieves the desired price.
     *
     * @return The desired price.
     */
    public String getDesiredPrice() {
        return desiredPrice;
    }

    /**
     * Sets the desired price.
     *
     * @param desiredPrice The desired price to set.
     */
    public void setDesiredPrice(String desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    /**
     * Retrieves the can exceed price value.
     *
     * @return The can exceed price value.
     */
    public Boolean getCanExceedPrice() {
        return canExceedPrice;
    }

    /**
     * Sets the can exceed price value.
     *
     * @param canExceedPrice The can exceed price value to set.
     */
    public void setCanExceedPrice(Boolean canExceedPrice) {
        this.canExceedPrice = canExceedPrice;
    }

    /**
     * Retrieves the should stop device value.
     *
     * @return The should stop device value.
     */
    public Boolean getShouldStopDevice() {
        return shouldStopDevice;
    }

    /**
     * Sets the should stop device value.
     *
     * @param shouldStopDevice The should stop device value to set.
     */
    public void setShouldStopDevice(Boolean shouldStopDevice) {
        this.shouldStopDevice = shouldStopDevice;
    }
    /**
     * Returns a string representation of the schedule.
     *
     * @return The string representation of the schedule.
     */

    @Override
    public String toString() {
        StringBuilder jsonBuilder = new StringBuilder();
        scheduleBeginn = scheduleBeginn.replace("D","Z");
        scheduleEnd = scheduleEnd.replace("D","Z");
        runtime = Duration.ofMinutes(Math.round(device.getRuntime()));
        jsonBuilder.append("{\n");
        jsonBuilder.append("    \"deviceID\": ").append(device.getDeviceID()).append(",\n");
        jsonBuilder.append("    \"smartHomeID\": ").append(smartHomeId).append(",\n");
        jsonBuilder.append("    \"powerConsumptionKWH\": ").append(device.getElectricityBasicUsage()).append(",\n");
        jsonBuilder.append("    \"duration\": \"").append(runtime.toString()).append("\",\n");
        jsonBuilder.append("    \"startTime\": \"").append(scheduleBeginn).append("\",\n");
        jsonBuilder.append("    \"endTime\": \"").append(scheduleEnd).append("\",\n");
        jsonBuilder.append("    \"desiredPrice\": ").append(desiredPrice).append(",\n");
        jsonBuilder.append("    \"canExceedPrice\": ").append(canExceedPrice).append(",\n");
        jsonBuilder.append("    \"shouldStopDevice\": ").append(shouldStopDevice).append("\n");
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}

