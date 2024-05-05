package com.codigo.msArellanoVilchez.infraestructure.dao;

import com.codigo.msArellanoVilchez.infraestructure.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
    Optional<EmpresaEntity> findByNumeroDocumento(String numeroDocumento);
}
