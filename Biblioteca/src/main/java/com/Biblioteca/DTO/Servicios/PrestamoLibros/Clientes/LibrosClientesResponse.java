package com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LibrosClientesResponse implements Serializable {

    private Date fechaEntrega;

    private Date fechaDev;

    private Long idCliente;

    private Long idLibro;
}
