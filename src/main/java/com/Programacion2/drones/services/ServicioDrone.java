package com.Programacion2.drones.services;

import com.Programacion2.drones.entities.Drone;
import com.Programacion2.drones.repositories.RepositorioDrone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service

public class ServicioDrone implements ServicioBase<Drone> {

    @Autowired
    private RepositorioDrone repositorio;

    @Override
    @Transactional
    public List<Drone> findAll() throws Exception {
        try {
            List<Drone> entities = this.repositorio.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Drone findById(long id) throws Exception {
        try {
            Optional<Drone> opt = this.repositorio.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Drone saveOne(Drone entity) throws Exception {
        try {
            Drone drone = this.repositorio.save(entity);
            return drone;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Drone updateOne(Drone entity, long id) throws Exception {
        try {
            Optional<Drone> opt = this.repositorio.findById(id);
            Drone drone = opt.get();
            drone = this.repositorio.save(entity);
            return drone;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            Optional<Drone> opt = this.repositorio.findById(id);
            if (!opt.isEmpty()) {
                Drone drone = opt.get();
                drone.setActivo(!drone.isActivo());
                this.repositorio.save(drone);
            } else {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /*   Metodos nuevos   */

    @Transactional
    public List<Drone> findAllByActivo() throws Exception{
        try {
            List<Drone> entities = this.repositorio.findAllByActivo();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Drone findByIdAndActivo(long id) throws Exception {
        try {
            Optional<Drone> opt = this.repositorio.findByIdAndActivo(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Drone> findByTitle(String q) throws Exception{
        try{
            List<Drone> entities = this.repositorio.findByTitle(q);
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}

