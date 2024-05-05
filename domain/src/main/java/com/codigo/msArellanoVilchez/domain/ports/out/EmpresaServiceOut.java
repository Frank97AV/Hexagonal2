package com.codigo.msArellanoVilchez.domain.ports.out;

import com.codigo.msArellanoVilchez.domain.aggregates.dto.EmpresaDto;
import com.codigo.msArellanoVilchez.domain.aggregates.request.EmpresaRequest;

import java.util.List;
import java.util.Optional;

public interface EmpresaServiceOut {
    EmpresaDto crearEmpresaOut(EmpresaRequest empresaRequest);
    Optional<EmpresaDto> buscarEmpresaPorIdOut(Long id);
    List<EmpresaDto> buscarEmpresasTodosOut();
    EmpresaDto actualizarEmpresaOut (Long id, EmpresaRequest empresaRequest);
    EmpresaDto eliminarEmpresaOut (Long id);

}
