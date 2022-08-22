package com.Biblioteca.Controller;

import com.Biblioteca.DTO.CursoTaller.TallerFecha;
import com.Biblioteca.DTO.CursoTaller.TallerRequest;
import com.Biblioteca.DTO.CursoTaller.TallerResponse;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Service.TallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


    @CrossOrigin(origins= {"http://localhost:4200"})
    @RestController
    @RequestMapping("/api/taller")
    public class TallerController {
        @Autowired
        private TallerService tallerService;

        @PostMapping("/registrarTaller")
        public ResponseEntity<?> registroTaller(@RequestBody TallerRequest request) {
            return new ResponseEntity<>(tallerService.registrarTaller(request), HttpStatus.OK);
        }

        @PutMapping
        public ResponseEntity<?> updateTaller(@RequestBody TallerRequest tallerRequest) {
            tallerService.actualizartalleres(tallerRequest);
            return new ResponseEntity(new Mensaje("Taller Actualizado"), HttpStatus.OK);
        }

        @GetMapping("/allTalleres")
        public ResponseEntity<List<TallerResponse>> listAllTalleres() {
            List<TallerResponse> taller = tallerService.listAllTalleres();
            return new ResponseEntity<List<TallerResponse>>(taller, HttpStatus.OK);
        }

        @GetMapping("/allByfechaInicio/{fechaInicio}")
        public ResponseEntity<List<TallerFecha>> listAllTalleresfecha(@PathVariable("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio){
            List<TallerFecha> curso = tallerService.listaPorft(fechaInicio);
            return new ResponseEntity<>(curso, HttpStatus.OK);
        }
    }
