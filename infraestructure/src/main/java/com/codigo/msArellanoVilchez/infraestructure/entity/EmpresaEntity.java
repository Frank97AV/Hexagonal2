package com.codigo.msArellanoVilchez.infraestructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "empresa_info")
@Getter
@Setter
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idEmpresa;

    @Column(name = "razonsocial", nullable = false, length = 255)
    private String razonSocial;

    @Column(name = "tipodocumento", nullable = false, length = 5)
    private String tipoDocumento;

    @Column(name = "numerodocumento", nullable = false, length = 20)
    private String numeroDocumento;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "condicion", nullable = false, length = 50)
    private String condicion;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "distrito", nullable = false, length = 100)
    private String distrito;

    @Column(name = "provincia", nullable = false, length = 100)
    private String provincia;

    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;

    @Column(name = "esagenteretencion", nullable = false)
    private boolean EsAgenteRetencion;

    @Column(name = "usuacrea", length = 45)
    private String usuaCrea;

    @Column(name = "datecreate")
    private Timestamp dateCreate;

    @Column(name = "usuamodif", length = 45)
    private String usuaModif;

    @Column(name = "datemodif")
    private Timestamp dateModif;

    @Column(name = "usuadelet", length = 45)
    private String usuaDelet;

    @Column(name = "datedelet")
    private Timestamp dateDelet;
}
