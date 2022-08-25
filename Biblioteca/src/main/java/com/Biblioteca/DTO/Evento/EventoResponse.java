package com.Biblioteca.DTO.Evento;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EventoResponse implements Serializable {
    private Long id;
    private String descripcion;
    private Date fecha;
    private String actividades;
    private Long numParticipantes;
    private String documento;
    private Long usuarioid;
}