package com.Biblioteca.Controller;

import com.Biblioteca.DTO.Persona.PersonaClienteResponse;
import com.Biblioteca.DTO.Reporte.Reportesd;
import com.Biblioteca.Service.PersonaService;
import com.Biblioteca.Service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/reporte")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;
    @GetMapping("/all/{mes}/{anio}")
    public ResponseEntity<List<Reportesd>> listAll(@PathVariable Long mes, @PathVariable Long anio) {
        List<Reportesd> clientes = reporteService.listAllbymes(mes,anio);
        return new ResponseEntity<List<Reportesd>>(clientes, HttpStatus.OK);
    }
}