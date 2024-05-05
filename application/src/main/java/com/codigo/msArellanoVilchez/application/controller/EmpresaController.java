package com.codigo.msArellanoVilchez.application.controller;

import com.codigo.msArellanoVilchez.domain.aggregates.dto.EmpresaDto;
import com.codigo.msArellanoVilchez.domain.aggregates.request.EmpresaRequest;
import com.codigo.msArellanoVilchez.domain.ports.in.EmpresaServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ms-arellano-vilchez/v1/empresa")
@AllArgsConstructor
public class EmpresaController {

    private final EmpresaServiceIn empresaServiceIn;

    @PostMapping
    public ResponseEntity<EmpresaDto> registrar(@RequestBody EmpresaRequest empresaRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.crearEmpresaIn(empresaRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDto> buscarPorId(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.buscarEmpresaPorIdIn(id).get());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<EmpresaDto>> buscartodos(){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.buscarEmpresasTodosIn());
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> actualizar(@PathVariable Long id, @RequestBody EmpresaRequest empresaRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.actualizarEmpresaIn(id,empresaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmpresaDto> eliminar(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.eliminarEmpresaIn(id));
    }

}
