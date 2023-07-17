package de.backend.smarthome_backend.repository;

import de.backend.smarthome_backend.entity.TarifEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TarifEntityRepository extends JpaRepository<TarifEntity, Long> {
}