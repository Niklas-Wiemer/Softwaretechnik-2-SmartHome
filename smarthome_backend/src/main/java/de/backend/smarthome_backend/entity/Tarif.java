package de.backend.smarthome_backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a tariff with a price.
 */
public class Tarif {
    private double price;

    /**
     * Constructs a Tarif object with the given price.
     *
     * @param price The price of the tariff.
     */
    public Tarif(double price) {
        this.price = price;
    }

    /**
     * Constructs a Tarif object with the price obtained from JSON serialization.
     *
     * @param price The price of the tariff obtained from JSON.
     */
    public Tarif(@JsonProperty("price") Double price) {
        this.price = price;
    }

    /**
     * Retrieves the price of the tariff.
     *
     * @return The price of the tariff.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the tariff.
     *
     * @param price The price of the tariff.
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
