package com.Biblioteca.DTO.Persona;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class PersonaClienteResponse implements Serializable {

    private Long id;

    private Long idCliente;

    private String cedula;

    private String apellidos;

    private String nombres;

    private Date fechaNacimiento;

    private Long edad;

    private String genero;

    private String telefono;

    private String email;

    private String estadoCivil;

    private Boolean discapacidad;

    private Long idBarrio;
    private String barrio;

    private Long idParroquia;
    private String parroquia;


    private Long idCanton;
    private String canton;

    private Long idProvincia;
    private String provincia;


    public PersonaClienteResponse(Long id, String cedula, String apellidos, String nombres, Date fechaNacimiento, Long edad, String genero, String telefono, String email, String estadoCivil, Boolean discapacidad, String barrio, String parroquia, String canton, String provincia) {
        this.id = id;
        this.cedula = cedula;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.genero = genero;
        this.telefono = telefono;
        this.email = email;
        this.estadoCivil = estadoCivil;
        this.discapacidad = discapacidad;
        this.barrio = barrio;
        this.parroquia = parroquia;
        this.canton = canton;
        this.provincia = provincia;
    }


    public PersonaClienteResponse() {
    }
}
