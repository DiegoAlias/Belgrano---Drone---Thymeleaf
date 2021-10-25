package com.Programacion2.drones.repositories;

import com.Programacion2.drones.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioDrone extends JpaRepository<Drone, Long> {
    @Query(value = "SELECT * FROM drones  WHERE drones.activo = true", nativeQuery = true)
    List<Drone> findAllByActivo();

    @Query(value = "SELECT * FROM drones  WHERE drones.id = :id AND drones.activo = true", nativeQuery = true)
    Optional<Drone> findByIdAndActivo(@Param("id") long id);

    @Query(value = "SELECT * FROM drones WHERE drones.modelo LIKE %:q% AND drones.activo =true", nativeQuery = true)
    List<Drone> findByTitle(@Param("q")String q);

}
