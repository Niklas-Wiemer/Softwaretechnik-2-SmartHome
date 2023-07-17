package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.Tarif;
import de.backend.smarthome_backend.entity.TarifEntity;

import java.util.List;

/**
 * The TarifService interface provides methods to manage TarifEntity objects.
 */
public interface TarifService {

    /**
     * Saves a TarifEntity object.
     *
     * @param tarifEntity The TarifEntity object to be saved.
     * @return The saved TarifEntity object.
     */
    TarifEntity saveTarifEntity(TarifEntity tarifEntity);

    /**
     * Fetches a list of TarifEntity objects.
     *
     * @return A list of TarifEntity objects.
     */
    List<TarifEntity> fetchTarifEntityList();

    /**
     * Updates a TarifEntity object with the provided ID.
     *
     * @param tarifEntity The updated TarifEntity object.
     * @param id          The ID of the TarifEntity to be updated.
     */
    void updateTarifEntity(TarifEntity tarifEntity, Long id);

    /**
     * Deletes a TarifEntity object with the provided ID.
     *
     * @param id The ID of the TarifEntity to be deleted.
     */
    void deleteTarifEntity(Long id);
}
