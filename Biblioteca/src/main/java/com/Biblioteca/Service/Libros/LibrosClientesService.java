package com.Biblioteca.Service.Libros;


import com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes.LibrosClientesRequest;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibros;
import com.Biblioteca.Repository.Libros.LibroClienteRepository;
import com.Biblioteca.Repository.Libros.PrestamoLibrosRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import com.Biblioteca.Repository.Persona.PersonaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class LibrosClientesService {

    @Autowired
    private LibroClienteRepository libroClienteRepository;


    //LIBROS
    @Autowired
    private PrestamoLibrosRepository prestamoLibrosRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public boolean registroPrestamos(LibrosClientesRequest request) {
        Optional<Cliente> cliente = clienteRepository.findById(request.getIdCliente());
        if (cliente.isPresent()) {
            Optional<PrestamoLibros> libro = prestamoLibrosRepository.findById(request.getIdLibro());
            if (libro.isPresent()) {
                if (!libro.get().getEstado()) {
                    PrestamoLibroCliente newLibroCliente = new PrestamoLibroCliente();
                    newLibroCliente.setDia((long) request.getFechaEntrega().getDate() + 1);
                    newLibroCliente.setMes((long) request.getFechaEntrega().getMonth() + 1);
                    newLibroCliente.setAnio((long) request.getFechaEntrega().getYear() + 1900);
                    newLibroCliente.setCliente(cliente.get());
                    newLibroCliente.setPrestamo(libro.get());
                    try {
                        libroClienteRepository.save(newLibroCliente);
                        return true;
                    } catch (Exception ex) {
                        throw new BadRequestException("No se guardó el cliente/libro" + ex);
                    }
                } else {
                    new Mensaje("El libro está en préstamo");

                }
            } else {
                throw new BadRequestException("No existe un libro con id" + request.getIdLibro());
            }

        } else {
            throw new BadRequestException("No existe cliente con id" + request.getIdCliente());
        }
        return false;
    }
}
