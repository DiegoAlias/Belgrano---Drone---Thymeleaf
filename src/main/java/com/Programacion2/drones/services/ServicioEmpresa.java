package com.Programacion2.drones.services;

import com.Programacion2.drones.entities.Empresa;
import com.Programacion2.drones.repositories.RepositorioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service

public class ServicioEmpresa implements ServicioBase<Empresa>{

    @Autowired
    private RepositorioEmpresa repositorio;

    @Override
    @Transactional
    public List<Empresa> findAll() throws Exception {
        try {
            List<Empresa> empresa = this.repositorio.findAll();
            return empresa;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Empresa findById(long id) throws Exception {
        try {
            Optional<Empresa> opt = this.repositorio.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Empresa saveOne(Empresa entity) throws Exception {
        try {
            Empresa empresa = this.repositorio.save(entity);
            return empresa;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Empresa updateOne(Empresa entity, long id) throws Exception {
        try {
            Optional<Empresa> opt = this.repositorio.findById(id);
            Empresa empresa = opt.get();
            empresa = this.repositorio.save(entity);
            return empresa;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            Optional<Empresa> opt = this.repositorio.findById(id);

            if (!opt.isEmpty()) {
                Empresa empresa = opt.get();
                empresa.setActivo(!empresa.isActivo());
                this.repositorio.save(empresa);
            } else {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
