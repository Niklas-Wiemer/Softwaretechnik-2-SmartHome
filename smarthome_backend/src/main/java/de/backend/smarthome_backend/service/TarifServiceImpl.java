package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.Tarif;
import de.backend.smarthome_backend.entity.TarifEntity;
import de.backend.smarthome_backend.repository.TarifEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TarifServiceImpl implements TarifService {
    private final TarifEntityRepository tarifRepository;

    @Autowired
    public TarifServiceImpl(TarifEntityRepository tarifRepository) {
        this.tarifRepository = tarifRepository;
    }

    /**
     * Saves a TarifEntity object.
     *
     * @param tarifEntity The TarifEntity object to be saved.
     * @return The saved TarifEntity object.
     */
    @Override
    public TarifEntity saveTarifEntity(TarifEntity tarifEntity) {
        return tarifRepository.save(tarifEntity);
    }

    /**
     * Fetches a list of all TarifEntity objects.
     *
     * @return A list of TarifEntity objects.
     */
    @Override
    public List<TarifEntity> fetchTarifEntityList() {
        return tarifRepository.findAll();
    }

    /**
     * Updates a TarifEntity object with the provided data.
     *
     * @param tarifEntity The TarifEntity object containing the updated data.
     * @param id          The ID of the TarifEntity object to be updated.
     * @throws IllegalArgumentException if the TarifEntity with the given ID is not found.
     */
    @Override
    public void updateTarifEntity(TarifEntity tarifEntity, Long id) {
        Optional<TarifEntity> optionalTarif = tarifRepository.findById(id);
        if (optionalTarif.isPresent()) {
            TarifEntity tarif = optionalTarif.get();
            tarif.setTime(tarifEntity.getTime());
            tarif.setDate(tarifEntity.getDate());
            tarif.setElectricityPrice(tarifEntity.getElectricityPrice());
        } else {
            throw new IllegalArgumentException("Tarif with ID " + id + " not found.");
        }
    }

    /**
     * Deletes a TarifEntity object with the specified ID.
     *
     * @param id The ID of the TarifEntity object to be deleted.
     */
    @Override
    public void deleteTarifEntity(Long id) {
        tarifRepository.deleteById(id);
    }
}
