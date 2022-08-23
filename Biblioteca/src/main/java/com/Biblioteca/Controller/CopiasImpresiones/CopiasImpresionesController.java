package com.Biblioteca.Controller.CopiasImpresiones;


import com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes.CopiasClienteRequest;
import com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes.CopiasClienteResponse;
import com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes.LibrosClientesRequest;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Service.Copias.CopiasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/copias")
public class CopiasImpresionesController {

    @Autowired
    private CopiasService copiasService;

    @PostMapping
    public ResponseEntity<CopiasClienteResponse> create(@RequestBody CopiasClienteRequest request){
        CopiasClienteResponse copias= copiasService.registroCopiasCliente(request);
        return new ResponseEntity<>(copias, HttpStatus.CREATED);
    }
}
