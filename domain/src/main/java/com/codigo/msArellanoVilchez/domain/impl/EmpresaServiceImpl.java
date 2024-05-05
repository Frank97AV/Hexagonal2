package com.codigo.msArellanoVilchez.domain.impl;

import com.codigo.msArellanoVilchez.domain.aggregates.dto.EmpresaDto;
import com.codigo.msArellanoVilchez.domain.aggregates.request.EmpresaRequest;
import com.codigo.msArellanoVilchez.domain.ports.in.EmpresaServiceIn;
import com.codigo.msArellanoVilchez.domain.ports.out.EmpresaServiceOut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor

public class EmpresaServiceImpl implements EmpresaServiceIn {
    private final EmpresaServiceOut empresaServiceOut;
    @Override
    public EmpresaDto crearEmpresaIn(EmpresaRequest empresaRequest) {
        return empresaServiceOut.crearEmpresaOut(empresaRequest);
    }

    @Override
    public Optional<EmpresaDto> buscarEmpresaPorIdIn(Long id) {
        return empresaServiceOut.buscarEmpresaPorIdOut(id);
    }

    @Override
    public List<EmpresaDto> buscarEmpresasTodosIn() {
        return empresaServiceOut.buscarEmpresasTodosOut();
    }

    @Override
    public EmpresaDto actualizarEmpresaIn(Long id, EmpresaRequest empresaRequest) {
        return empresaServiceOut.actualizarEmpresaOut(id, empresaRequest);
    }

    @Override
    public EmpresaDto eliminarEmpresaIn(Long id) {
        return empresaServiceOut.eliminarEmpresaOut(id);
    }
}
