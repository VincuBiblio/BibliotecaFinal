package com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CopiasClienteRequest implements Serializable {
    private Date fecha;

    private Long idCliente;


    private Long pagBlanco, pagColor;
}
