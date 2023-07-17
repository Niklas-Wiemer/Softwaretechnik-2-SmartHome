package de.backend.smarthome_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Represents device usage statistics for a specific run.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRunStats {


    /**
     * The unique identifier for the device usage statistics.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The device associated with the usage statistics.
     */
    @ManyToOne()
    @JoinColumn(name = "device_id")
    private Device device;

    /**
     * The date for which the usage statistics are recorded (yyyy-MM-dd format).
     */
    @Column(name = "date")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String date;
    /**
     * The average electricity usage for the run.
     */
    @Column(name = "electricity_usage_for_run")
    private Double electricityUsageForRun;
    /**
     * The average electricity usage for the day.
     */
    @Column(name = "average_electricity_usage")
    private Double averageElectricityUsage;

    /**
     * The cost of the Run by the Current Tarif
     */
    @Column(name = "cost_of_the_run")
    private Double costOfTheRun;
    /**
     * The Tarif of the Run
     */
    @Column(name = "tarif_Price")
    private Double tarifPrice;

    public DeviceRunStats(Device deviceById, String currentDate, Double electricityConsume, Double avgElectricity,Double costOfTheRun,Double tarifPrice) {
        this.device = deviceById;
        this.date = currentDate;
        this.electricityUsageForRun = electricityConsume;
        this.averageElectricityUsage = avgElectricity;
        this.costOfTheRun = costOfTheRun;
        this.tarifPrice = tarifPrice;
    }

    /**
     * Retrieves the unique identifier for the device usage statistics.
     *
     * @return The ID of the statistics.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the device usage statistics.
     *
     * @param id The ID of the statistics.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the device associated with the usage statistics.
     *
     * @return The device object.
     */
    public Device getDevice() {
        return device;
    }

    /**
     * Sets the device associated with the usage statistics.
     *
     * @param device The device object.
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    /**
     * Retrieves the date for which the usage statistics are recorded.
     *
     * @return The date in yyyy-MM-dd format.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for which the usage statistics are recorded.
     *
     * @param date The date in yyyy-MM-dd format.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retrieves the average electricity usage for the day.
     *
     * @return The average electricity usage.
     */
    public Double getAverageElectricityUsage() {
        return averageElectricityUsage;
    }

    /**
     * Sets the average electricity usage for the day.
     *
     * @param averageElectricityUsage The average electricity usage.
     */
    public void setAverageElectricityUsage(Double averageElectricityUsage) {
        this.averageElectricityUsage = averageElectricityUsage;
    }
    /**
     * Retrieves the Electricity usage for the Run.
     *
     * @return The Electricity usage.
     */
    public Double getElectricityUsageForRun() {
        return electricityUsageForRun;
    }
    /**
     * Sets the electricity usage for the run.
     *
     * @param electricityUsageForRun The Electricity Usage for Run.
     */
    public void setElectricityUsageForRun(Double electricityUsageForRun) {
        this.electricityUsageForRun = electricityUsageForRun;
    }
    /**
     * Retrieves the Cost of the Run.
     *
     * @return The Cost of the Run.
     */
    public Double getCostOfTheRun() {
        return costOfTheRun;
    }
    /**
     * Sets the Cost of the Run .
     *
     * @param costOfTheRun The cost of the Run .
     */
    public void setCostOfTheRun(Double costOfTheRun) {
        this.costOfTheRun = costOfTheRun;
    }

    public Double getTarifPrice() {
        return tarifPrice;
    }

    public void setTarifPrice(Double tarifPrice) {
        this.tarifPrice = tarifPrice;
    }
}
