package com.codigo.msArellanoVilchez.infraestructure.adapter;

import com.codigo.msArellanoVilchez.domain.aggregates.constans.Constans;
import com.codigo.msArellanoVilchez.domain.aggregates.dto.PersonaDto;
import com.codigo.msArellanoVilchez.domain.aggregates.dto.ReniecDto;
import com.codigo.msArellanoVilchez.domain.aggregates.request.PersonaRequest;
import com.codigo.msArellanoVilchez.domain.ports.out.PersonaServiceOut;
import com.codigo.msArellanoVilchez.infraestructure.client.ClientReniec;
import com.codigo.msArellanoVilchez.infraestructure.dao.EmpresaRepository;
import com.codigo.msArellanoVilchez.infraestructure.dao.PersonaRepository;
import com.codigo.msArellanoVilchez.infraestructure.entity.EmpresaEntity;
import com.codigo.msArellanoVilchez.infraestructure.entity.PersonaEntity;
import com.codigo.msArellanoVilchez.infraestructure.mapper.PersonaMapper;
import com.codigo.msArellanoVilchez.infraestructure.redis.RedisService;
import com.codigo.msArellanoVilchez.infraestructure.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaServiceOut {
    private final PersonaRepository personaRepository;
    private final ClientReniec clientReniec;
    private final RedisService redisService;
    private final EmpresaRepository empresaRepository;
    @Value("${token.api}")
    private String tokenReniec;
    @Override
    public PersonaDto crearPersonaOut(PersonaRequest personaRequest) {
        Optional<EmpresaEntity> empresaEntity = empresaRepository.findByNumeroDocumento(String.valueOf(personaRequest.getEmpresa()));
        if (empresaEntity.isPresent()) {
            PersonaEntity personaEntity = getEntity(personaRequest, Constans.CREAR, null);
            personaEntity.setEmpresaEntity(empresaEntity.get());
            return PersonaMapper.fromEntity(personaRepository.save(personaEntity));
        } else {
            throw new RuntimeException();
        }

    }

    private PersonaEntity getEntity(PersonaRequest personaRequest, String operacion, Long id){
        //Exec servicio
        ReniecDto reniecDto = getExecReniec(personaRequest.getNumDoc());
        PersonaEntity entity = new PersonaEntity();

        entity.setNombre(reniecDto.getNombres());
        entity.setApellido(reniecDto.getApellidoPaterno()+" "+ reniecDto.getApellidoMaterno());
        entity.setTipoDocumento(reniecDto.getTipoDocumento());
        entity.setNumeroDocumento(reniecDto.getNumeroDocumento());
        entity.setEmail("a"+reniecDto.getApellidoPaterno()+"@gmail.com");
        entity.setTelefono(Constans.TELEFONO);
        entity.setDireccion(Constans.DIRECCION);
        entity.setEstado(Constans.STATUS_ACTIVE);

        /*
        if(actualiza){
            //si Actualizo hago esto
            entity.setIdPersona(id);
            entity.setUsuaModif(Constans.USU_ADMIN);
            entity.setDateModif(getTimestamp());

        }else{
            //Sino Actualizo hago esto
            entity.setUsuaCrea(Constans.USU_ADMIN);
            entity.setDateCreate(getTimestamp());
        }
         */
        switch (operacion){
            case Constans.CREAR:
                entity.setIdPersona(id);
                entity.setUsuaModif(Constans.USU_ADMIN);
                entity.setDateModif(getTimestamp());
                break;
            case Constans.ACTUALIZAR:
                entity.setUsuaCrea(Constans.USU_ADMIN);
                entity.setDateCreate(getTimestamp());
                break;
        }

        return entity;
    }


    private ReniecDto getExecReniec(String numDoc){
        String authorization = "Bearer "+tokenReniec;
        return clientReniec.getInfoReniec(numDoc,authorization);
    }
    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }
    @Override
    public Optional<PersonaDto> buscarPersonaPorIdOut(Long id) {
        String redisInfo = redisService.getFromRedis(Constans.REDIS_KEY_OBTENERPERSONA+id);
        if(redisInfo!= null){
            PersonaDto personaDto = Util.convertirDesdeString(redisInfo,PersonaDto.class);
            return Optional.of(personaDto);
        }else{
            PersonaDto personaDto = PersonaMapper.fromEntity(personaRepository.findById(id).get());
            String dataForRedis = Util.convertirAString(personaDto);
            redisService.saveInRedis(Constans.REDIS_KEY_OBTENERPERSONA+id,dataForRedis,2);
            return Optional.of(personaDto);
        }
    }

    @Override
    public List<PersonaDto> buscarTodosOut() {
        List<PersonaDto> listaDto = new ArrayList<>();
        List<PersonaEntity> entidades = personaRepository.findAll();
        for (PersonaEntity dato :entidades){
            listaDto.add(PersonaMapper.fromEntity(dato));
        }
        return listaDto;
    }

    @Override
    public PersonaDto actualizarPersonaOut(Long id, PersonaRequest personaRequest) {
        Optional<PersonaEntity> datoExtraido = personaRepository.findById(id);
        Optional<EmpresaEntity> empresaEntity = empresaRepository.findByNumeroDocumento(String.valueOf(personaRequest.getEmpresa()));
        if(datoExtraido.isPresent()){
            PersonaEntity personaEntity = getEntity(personaRequest,Constans.ACTUALIZAR, id);
            personaEntity.setEmpresaEntity(empresaEntity.get());
            personaEntity.setUsuaCrea(datoExtraido.get().getUsuaCrea());
            personaEntity.setDateCreate(datoExtraido.get().getDateCreate());
            return PersonaMapper.fromEntity(personaRepository.save(personaEntity));
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public PersonaDto eliminarPersonaOut(Long id) {
        Optional<PersonaEntity> datoExtraido = personaRepository.findById(id);
        if(datoExtraido.isPresent()){
            datoExtraido.get().setEstado(0);
            datoExtraido.get().setUsuaDelet(Constans.USU_ADMIN);
            datoExtraido.get().setDateDelet(getTimestamp());
            return PersonaMapper.fromEntity(personaRepository.save(datoExtraido.get()));
        }else {
            throw new RuntimeException();
        }
    }
}
