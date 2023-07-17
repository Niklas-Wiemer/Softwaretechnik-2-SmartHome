package de.backend.smarthome_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Represents device electricity production statistics for a specific day.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceElectricityProductionPerHour {
    public DeviceElectricityProductionPerHour(Device device, String date, String hour, Double electricityProduction) {
        this.device = device;
        this.date = date;
        this.hour = hour;
        this.electricityProduction = electricityProduction;
    }

    /**
     * The unique identifier for the device electricity production statistics.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The device associated with the electricity production statistics.
     */
    @ManyToOne()
    @JoinColumn(name = "device_id")
    private Device device;

    /**
     * The date for which the electricity production statistics are recorded (yyyy-MM-dd format).
     */
    @Column(name = "time_date")
    private String date;

    /**
     * The Hour of the Production (hh-mm-ss format).
     */
    @Column(name = "time_hour")
    private String hour;

    /**
     * The electricity production for the Hour.
     */
    @Column(name = "electricity_production")
    private Double electricityProduction;

    /**
     * Retrieves the unique identifier for the device electricity production statistics.
     *
     * @return The ID of the statistics.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the device electricity production statistics.
     *
     * @param id The ID of the statistics.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the device associated with the electricity production statistics.
     *
     * @return The device object.
     */
    public Device getDevice() {
        return device;
    }

    /**
     * Sets the device associated with the electricity production statistics.
     *
     * @param device The device object.
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    /**
     * Retrieves the date for which the electricity production statistics are recorded.
     *
     * @return The date in yyyy-MM-dd format.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for which the electricity production statistics are recorded.
     *
     * @param date The date in yyyy-MM-dd format.
     */
    public void setDate(String date) {
        this.date = date;
    }
    /**
     * Retrieves the Electricity Production for the Hour.
     *
     * @return electricityProduction for thr Hour.
     */
    public Double getElectricityProduction() {
        return electricityProduction;
    }
    /**
     * Sets the Electricity Production for the Hour.
     *
     * @param electricityProduction for the Hour
     */
    public void setElectricityProduction(Double electricityProduction) {
        this.electricityProduction = electricityProduction;
    }
    /**
     * Retrieves the Hour of the Electricity Production.
     *
     * @return hour of the Production.
     */
    public String getHour() {
        return hour;
    }
    /**
     * Sets the hour of the Electricity Production.
     *
     * @param hour for the Hour
     */
    public void setHour(String hour) {
        this.hour = hour;
    }
}