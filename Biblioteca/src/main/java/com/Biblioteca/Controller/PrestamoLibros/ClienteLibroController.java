package com.Biblioteca.Controller.PrestamoLibros;


import com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes.LibrosClientesRequest;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Service.Libros.LibrosClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/libro/cliente")
public class ClienteLibroController {

    @Autowired
    private LibrosClientesService librosClientesService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LibrosClientesRequest request){
        librosClientesService.registroPrestamos(request);
        return new ResponseEntity(new Mensaje("Libro-Cliente Creado"), HttpStatus.CREATED);
    }
}
