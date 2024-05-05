package com.codigo.msArellanoVilchez.infraestructure.util;

import com.codigo.msArellanoVilchez.domain.aggregates.dto.EmpresaDto;
import com.codigo.msArellanoVilchez.domain.aggregates.dto.PersonaDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static String convertirAString(PersonaDto personaDto){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(personaDto);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public static String convertirAString(EmpresaDto empresaDto){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(empresaDto);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
    /*
    public static String convertirAString(Class aclas){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(aclas);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
     */


    public static <T> T convertirDesdeString(String json, Class<T> tipoClase){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json,tipoClase);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
