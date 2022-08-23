package com.Biblioteca.DTO.Servicios.PrestamoLibros;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrestamoLibrosRequest implements Serializable {

    private Long id;

    private String codigoLibro;

    private Boolean estado;
}
