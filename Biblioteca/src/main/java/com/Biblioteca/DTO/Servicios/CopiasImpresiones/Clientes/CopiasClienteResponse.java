package com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CopiasClienteResponse implements Serializable {

    private Date fechaEntrega;

    private Long idCliente;

    private Long idCopias;

    private Long pagBlanco, pagColor, pagTotal;

    public CopiasClienteResponse(Date fechaEntrega, Long idCliente, Long idCopias, Long pagBlanco, Long pagColor, Long pagTotal) {
        this.fechaEntrega = fechaEntrega;
        this.idCliente = idCliente;
        this.idCopias = idCopias;
        this.pagBlanco = pagBlanco;
        this.pagColor = pagColor;
        this.pagTotal = pagTotal;
    }
}
