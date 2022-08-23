package com.Biblioteca.DTO.CursoTaller;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TallerResponse implements Serializable {
    private Long id;
    private Long idTaller;
    private String nombre;
    private String lugar;
    private String descripcion;
    private String observaciones;
    private String responsable;
    private Date fechaInicio;
    private Date fechaFin;

    ////crear curso
    public TallerResponse(Long id, Long idTaller, String nombre, String lugar, String descripcion, String observaciones, String responsable, Date fechaInicio, Date fechaFin) {
        this.id=id;
        this.idTaller=idTaller;
        this.nombre=nombre;
        this.lugar=lugar;
        this.descripcion=descripcion;
        this.observaciones=observaciones;
        this.responsable=responsable;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
    }
    public TallerResponse() {
    }
}
