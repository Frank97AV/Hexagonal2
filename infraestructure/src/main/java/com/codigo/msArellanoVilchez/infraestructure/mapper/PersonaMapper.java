package com.codigo.msArellanoVilchez.infraestructure.mapper;


import com.codigo.msArellanoVilchez.domain.aggregates.dto.PersonaDto;
import com.codigo.msArellanoVilchez.infraestructure.entity.PersonaEntity;
import org.springframework.stereotype.Service;

@Service
public class PersonaMapper {

    public static PersonaDto fromEntity(PersonaEntity entity){
        PersonaDto dto = new PersonaDto();
        dto.setIdPersona(entity.getIdPersona());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTipoDocumento(entity.getTipoDocumento());
        dto.setNumDocu(entity.getNumeroDocumento());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setDireccion(entity.getDireccion());
        dto.setEstado(entity.getEstado());
        dto.setUsuaCrea(entity.getUsuaCrea());
        dto.setDateCreate(entity.getDateCreate());
        dto.setUsuaModif(entity.getUsuaModif());
        dto.setDateModif(entity.getDateModif());
        dto.setUsuaDelet(entity.getUsuaDelet());
        dto.setDateDelet(entity.getDateDelet());
        dto.setEmpresaDto(EmpresaMapper.fromEntity(entity.getEmpresaEntity()));
        return dto;

    }

}
