package com.Biblioteca.DTO.Reporte;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

    @Data
    public class Reportesd implements Serializable {
        private Long idcliente;
        private String codigo;
        private Long no;
        private String fecha;
        private String apellidos;
        private String nombres;
        private String cedula;
        private Date fechaNacimiento;
        private Long edad;
        private String genero;
        private String estadoCivil;
        private String provincia;
        private String canton;
        private String parroquia;
        private String barrio;
        private Boolean discapacidad;
        private String email;
        private String telefono;
        private Long repositorio;
        private Long biblioteca;
        private Long copias;
        private Long computo;
        private Long talleres;
        private Long idtaller;
        private String nombretaller;
        private String verificables;
    }