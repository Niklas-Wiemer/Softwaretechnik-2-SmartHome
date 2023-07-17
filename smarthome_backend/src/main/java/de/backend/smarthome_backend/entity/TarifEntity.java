package de.backend.smarthome_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarifEntity {
    /**
     * The unique ID of the tariff.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tarif_id", nullable = false)
    private Long tarifId;

    /**
     * The time for which the tariff is applicable.
     */
    @Column(name = "time")
    private String time;

    /**
     * The date for which the tariff is applicable.
     */
    @Column(name = "date")
    private String date;

    /**
     * The electricity price for the tariff.
     */
    @Column(name = "electricity_price")
    private Double electricityPrice;

    public TarifEntity(String time, String date, Double electricityPrice) {
        this.time = time;
        this.date = date;
        this.electricityPrice = electricityPrice;
    }

    /**
     * Retrieves the time for which the tariff is applicable.
     *
     * @return The time for the tariff.
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time for which the tariff is applicable.
     *
     * @param time The time for the tariff.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Retrieves the date for which the tariff is applicable.
     *
     * @return The date for the tariff.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for which the tariff is applicable.
     *
     * @param date The date for the tariff.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retrieves the unique ID of the tariff.
     *
     * @return The ID of the tariff.
     */
    public Long getTarifId() {
        return tarifId;
    }

    /**
     * Sets the unique ID of the tariff.
     *
     * @param tarifId The ID of the tariff.
     */
    public void setTarifId(Long tarifId) {
        this.tarifId = tarifId;
    }

    /**
     * Retrieves the electricity price for the tariff.
     *
     * @return The electricity price for the tariff.
     */
    public Double getElectricityPrice() {
        return electricityPrice;
    }

    /**
     * Sets the electricity price for the tariff.
     *
     * @param electricityPrice The electricity price for the tariff.
     */
    public void setElectricityPrice(Double electricityPrice) {
        this.electricityPrice = electricityPrice;
    }
}
