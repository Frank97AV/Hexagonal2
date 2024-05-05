package com.codigo.msArellanoVilchez.domain.ports.in;

import com.codigo.msArellanoVilchez.domain.aggregates.dto.EmpresaDto;
import com.codigo.msArellanoVilchez.domain.aggregates.request.EmpresaRequest;

import java.util.List;
import java.util.Optional;

public interface EmpresaServiceIn {
    EmpresaDto crearEmpresaIn(EmpresaRequest empresaRequest);
    Optional<EmpresaDto> buscarEmpresaPorIdIn(Long id);
    List<EmpresaDto> buscarEmpresasTodosIn();
    EmpresaDto actualizarEmpresaIn (Long id, EmpresaRequest empresaRequest);
    EmpresaDto eliminarEmpresaIn (Long id);

}
