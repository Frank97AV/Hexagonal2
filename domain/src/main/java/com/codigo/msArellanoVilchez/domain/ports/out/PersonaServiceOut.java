package com.codigo.msArellanoVilchez.domain.ports.out;

import com.codigo.msArellanoVilchez.domain.aggregates.dto.PersonaDto;
import com.codigo.msArellanoVilchez.domain.aggregates.request.PersonaRequest;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceOut {
    PersonaDto crearPersonaOut(PersonaRequest personaRequest);
    Optional<PersonaDto> buscarPersonaPorIdOut(Long id);
    List<PersonaDto> buscarTodosOut();
    PersonaDto actualizarPersonaOut(Long id, PersonaRequest personaRequest);
    PersonaDto eliminarPersonaOut(Long id);

}
