package com.example.ships.repositories;

import com.example.ships.models.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
    Optional<Ship> findShipByName(String name);

    List<Ship> findByUserId(Long id);

    List<Ship> findByUserIdNot(Long id);

    List<Ship> findByOrderByHealthAscNameAscPowerAsc();
}
