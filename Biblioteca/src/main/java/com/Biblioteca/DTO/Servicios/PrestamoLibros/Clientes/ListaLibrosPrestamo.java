package com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes;

import lombok.Data;

import java.io.Serializable;

@Data
public class ListaLibrosPrestamo implements Serializable {

    private Long idPrestamo;

    private String nombreCliente;

    private String cedulaCliente;

    private String fechaEntrega;

    private String fechaDev;

    private String codigoLibro;

    private Boolean estadoLibro;

}
