package com.codigo.msArellanoVilchez.infraestructure.adapter;

import com.codigo.msArellanoVilchez.domain.aggregates.constans.Constans;
import com.codigo.msArellanoVilchez.domain.aggregates.dto.EmpresaDto;
import com.codigo.msArellanoVilchez.domain.aggregates.dto.SunatDto;
import com.codigo.msArellanoVilchez.domain.aggregates.request.EmpresaRequest;

import com.codigo.msArellanoVilchez.domain.ports.out.EmpresaServiceOut;
import com.codigo.msArellanoVilchez.infraestructure.client.ClientSunat;
import com.codigo.msArellanoVilchez.infraestructure.dao.EmpresaRepository;
import com.codigo.msArellanoVilchez.infraestructure.entity.EmpresaEntity;;
import com.codigo.msArellanoVilchez.infraestructure.mapper.EmpresaMapper;
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
public class EmpresaAdapter implements EmpresaServiceOut {
    private final EmpresaRepository empresaRepository;
    private final ClientSunat clientSunat;
    private final RedisService redisService;
    @Value("${token.api}")
    private String tokenApi;
    @Override
    public EmpresaDto crearEmpresaOut(EmpresaRequest empresaRequest) {
        //
        EmpresaEntity empresaEntity = getEntity(empresaRequest, Constans.ACTUALIZAR, null);
        return EmpresaMapper.fromEntity(empresaRepository.save(empresaEntity));
    }

    private EmpresaEntity getEntity(EmpresaRequest empresaRequest, String operacion, Long id){
        SunatDto sunatDto= getExecSunat(empresaRequest.getNumDoc());
        EmpresaEntity entity = new EmpresaEntity();
        entity.setRazonSocial(sunatDto.getRazonSocial());
        entity.setTipoDocumento(sunatDto.getTipoDocumento());
        entity.setNumeroDocumento(sunatDto.getNumeroDocumento());
        entity.setEstado(Constans.STATUS_ACTIVE);
        entity.setCondicion(sunatDto.getCondicion());
        entity.setDireccion(sunatDto.getDireccion());
        entity.setDistrito(sunatDto.getDistrito());
        entity.setProvincia(sunatDto.getProvincia());
        entity.setDepartamento(sunatDto.getDepartamento());
        entity.setEsAgenteRetencion(sunatDto.isEsAgenteRetencion());

        /*
        if(equals(Constans.ACTUALIZAR)){
            entity.setIdEmpresa(id);
            entity.setUsuaModif(Constans.USU_ADMIN);
            entity.setDateModif(getTimestamp());

        }else {
            entity.setUsuaCrea(Constans.USU_ADMIN);
            entity.setDateCreate(getTimestamp());
        }
         */
        switch (operacion){
            case Constans.CREAR:
                entity.setIdEmpresa(id);
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

    private SunatDto getExecSunat(String numDoc) {
        String authorization = "Bearer " + tokenApi;
        return clientSunat.getInfoSunat(numDoc,authorization);
    }

    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }

    @Override
    public Optional<EmpresaDto> buscarEmpresaPorIdOut(Long id) {
        String redisInfo = redisService.getFromRedis(Constans.REDIS_KEY_OBTENEREMPRESA+id);
        if(redisInfo != null){
            EmpresaDto empresaDto = Util.convertirDesdeString(redisInfo, EmpresaDto.class);
            return Optional.of(empresaDto);
        } else {
            EmpresaDto empresaDto = EmpresaMapper.fromEntity(empresaRepository.findById(id).get());
            String dataForRedis = Util.convertirAString(empresaDto);
            redisService.saveInRedis(Constans.REDIS_KEY_OBTENEREMPRESA+id, dataForRedis, 2);
            return Optional.of(empresaDto);
        }
    }
    @Override
    public List<EmpresaDto> buscarEmpresasTodosOut() {
        List<EmpresaDto> listaDto = new ArrayList<>();
        List<EmpresaEntity> entidades = empresaRepository.findAll();
        for (EmpresaEntity dato : entidades) {
            listaDto.add(EmpresaMapper.fromEntity(dato));
        }
        return listaDto;
    }

    @Override
    public EmpresaDto actualizarEmpresaOut(Long id, EmpresaRequest empresaRequest) {
        Optional<EmpresaEntity> datoExtraido = empresaRepository.findById(id);
        if(datoExtraido.isPresent()){
            EmpresaEntity empresaEntity = getEntity(empresaRequest, Constans.ACTUALIZAR, id);
            empresaEntity.setUsuaCrea(datoExtraido.get().getUsuaCrea());
            empresaEntity.setDateCreate(datoExtraido.get().getDateCreate());
            return EmpresaMapper.fromEntity(empresaRepository.save(empresaEntity));
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public EmpresaDto eliminarEmpresaOut(Long id) {
        Optional<EmpresaEntity> datoExtraido = empresaRepository.findById(id);
        if(datoExtraido.isPresent()){
            datoExtraido.get().setEstado(0);
            datoExtraido.get().setUsuaDelet(Constans.USU_ADMIN);
            datoExtraido.get().setDateDelet(getTimestamp());
            return EmpresaMapper.fromEntity(empresaRepository.save(datoExtraido.get()));
        } else {
            throw new RuntimeException();
        }
    }
}
