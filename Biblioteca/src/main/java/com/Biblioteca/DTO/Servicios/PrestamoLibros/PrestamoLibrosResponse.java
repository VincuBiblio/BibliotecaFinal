package com.Biblioteca.DTO.Servicios.PrestamoLibros;

import lombok.Data;

import java.io.Serializable;

@Data

public class PrestamoLibrosResponse implements Serializable {
    private Long id;

    private String codigoLibro;

    private Boolean estado;

    public PrestamoLibrosResponse(Long id, String codigoLibro, Boolean estado) {
        this.id = id;
        this.codigoLibro = codigoLibro;
        this.estado = estado;
    }

    public PrestamoLibrosResponse() {

    }
}
