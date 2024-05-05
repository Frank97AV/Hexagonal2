package com.codigo.msArellanoVilchez.infraestructure.dao;

import com.codigo.msArellanoVilchez.infraestructure.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {

}
