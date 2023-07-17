package de.backend.smarthome_backend.controller;

import de.backend.smarthome_backend.entity.TarifEntity;
import de.backend.smarthome_backend.service.TarifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The TarifController class handles HTTP requests related to tariff entities.
 */
@RestController
@RequestMapping("/tarife")
public class TarifController {
    private final TarifService tarifService;


    /**
     * Constructs a new TarifController with the given dependency.
     *
     * @param tarifService The TarifService used for tariff operations.
     */
    @Autowired
    public TarifController(TarifService tarifService) {
        this.tarifService = tarifService;
    }

    /**
     * Saves a tariff entity.
     *
     * @param tarifEntity The tariff entity to be saved.
     * @return The saved tariff entity as a ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<TarifEntity> saveTarifEntity(@RequestBody TarifEntity tarifEntity) {
        TarifEntity savedTarif = tarifService.saveTarifEntity(tarifEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTarif);
    }

    /**
     * Retrieves a list of tariff entities.
     *
     * @return The list of tariff entities as a ResponseEntity.
     */
    @GetMapping
    public ResponseEntity<List<TarifEntity>> fetchTarifEntityList() {
        List<TarifEntity> tarifList = tarifService.fetchTarifEntityList();
        return ResponseEntity.ok(tarifList);
    }

    /**
     * Updates a tariff entity with the specified ID.
     *
     * @param tarifEntity The updated tariff entity.
     * @param id          The ID of the tariff entity to be updated.
     * @return A ResponseEntity indicating the success of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTarifEntity(@RequestBody TarifEntity tarifEntity, @PathVariable Long id) {
        try {
            tarifService.updateTarifEntity(tarifEntity, id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a tariff entity with the specified ID.
     *
     * @param id The ID of the tariff entity to be deleted.
     * @return A success message as a ResponseEntity.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifEntity(@PathVariable Long id) {
        tarifService.deleteTarifEntity(id);
        return ResponseEntity.ok().build();
    }
}
