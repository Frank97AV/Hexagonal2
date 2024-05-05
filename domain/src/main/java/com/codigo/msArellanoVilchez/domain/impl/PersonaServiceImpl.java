package com.codigo.msArellanoVilchez.domain.impl;

import com.codigo.msArellanoVilchez.domain.aggregates.dto.PersonaDto;
import com.codigo.msArellanoVilchez.domain.aggregates.request.PersonaRequest;
import com.codigo.msArellanoVilchez.domain.ports.in.PersonaServiceIn;
import com.codigo.msArellanoVilchez.domain.ports.out.PersonaServiceOut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonaServiceImpl implements PersonaServiceIn {
    private final PersonaServiceOut personaServiceOut;
    @Override
    public PersonaDto crearPersonaIn(PersonaRequest personaRequest) {
        return personaServiceOut.crearPersonaOut(personaRequest);
    }

    @Override
    public Optional<PersonaDto> buscarPersonaPorIdIn(Long id) {
        return personaServiceOut.buscarPersonaPorIdOut(id);
    }

    @Override
    public List<PersonaDto> buscarTodosIn() {
        return personaServiceOut.buscarTodosOut();
    }

    @Override
    public PersonaDto actualizarPersonaIn(Long id, PersonaRequest personaRequest) {
        return personaServiceOut.actualizarPersonaOut(id, personaRequest);
    }

    @Override
    public PersonaDto eliminarPersonaIn(Long id) {
        return personaServiceOut.eliminarPersonaOut(id);
    }
}
