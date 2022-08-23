package com.Biblioteca.DTO.CursoTaller;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CursoRequest implements Serializable {
    private Long id;
    private Long idCurso;
    private String nombre;
    private String lugar;
    private String descripcion;
    private String observaciones;
    private String responsable;
    private Date fechaInicio;
    private Date fechaFin;
    private String materiales;
    private Long numParticipantes;
    private String actividades;
}
