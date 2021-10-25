package com.Programacion2.drones.repositories;

import com.Programacion2.drones.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEmpresa extends JpaRepository<Empresa, Long> {
}
