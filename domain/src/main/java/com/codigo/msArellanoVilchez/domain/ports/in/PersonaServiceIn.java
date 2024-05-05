package com.codigo.msArellanoVilchez.domain.ports.in;

import com.codigo.msArellanoVilchez.domain.aggregates.dto.PersonaDto;
import com.codigo.msArellanoVilchez.domain.aggregates.request.PersonaRequest;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceIn {
    PersonaDto crearPersonaIn(PersonaRequest personaRequest);
    Optional<PersonaDto> buscarPersonaPorIdIn(Long id);
    List<PersonaDto> buscarTodosIn();
    PersonaDto actualizarPersonaIn(Long id, PersonaRequest personaRequest);
    PersonaDto eliminarPersonaIn(Long id);
}
