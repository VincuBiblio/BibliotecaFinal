package com.Biblioteca.Controller;


import com.Biblioteca.DTO.CursoTaller.Cliente_cursoResponse;
import com.Biblioteca.DTO.CursoTaller.CursoFecha;
import com.Biblioteca.DTO.CursoTaller.CursoRequest;
import com.Biblioteca.DTO.CursoTaller.CursoResponse;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Models.CursoTaller.CursoTaller;
import com.Biblioteca.Service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/curso")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @PostMapping("/registrarCurso")
    public ResponseEntity<?> registroCurso(@RequestBody CursoRequest request){
        return new ResponseEntity<>(cursoService.registrarCurso(request), HttpStatus.OK);
    }
    @PutMapping("/updatebyidcursotaller")
    public ResponseEntity<?> updateCurso(@RequestBody CursoRequest cursoRequest){
        cursoService.actualizarcursosconid_cursotaller(cursoRequest);
        return new ResponseEntity(new Mensaje("Curso Actualizado"), HttpStatus.OK);
    }
    @PutMapping("/updatebyidcurso")
    public ResponseEntity<?> actualizarcurso2(@RequestBody CursoRequest cursoRequest){
        cursoService.actualizarcursosconid_curso(cursoRequest);
        return new ResponseEntity(new Mensaje("Curso Actualizado.."), HttpStatus.OK);
    }

    @GetMapping("/allCursos")
    public ResponseEntity<List<CursoResponse>> listAllCursos(){
        List<CursoResponse> curso = cursoService.listAllCursos();
        return new ResponseEntity<List<CursoResponse>>(curso, HttpStatus.OK);
    }

    @GetMapping("/listbycursotaller/{id}")
    public ResponseEntity<CursoResponse> listCursobyIdcursotaller(@PathVariable Long id){
        CursoResponse curso = cursoService.listarcursobyIdCursoTaller(id);
        return new ResponseEntity<>(curso, HttpStatus.OK);
    }

    @GetMapping("/listarcursos/{id}")
    public ResponseEntity<CursoResponse> listCursobyidcurso(@PathVariable Long id){
        CursoResponse curso = cursoService.listarcursobyIdCurso(id);
        return new ResponseEntity<>(curso, HttpStatus.OK);
    }

    @GetMapping("/allByfechaInicio/{fechaInicio}")
    public ResponseEntity<List<CursoFecha>> listAllCursosfecha(@PathVariable("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio){
        List<CursoFecha> curso = cursoService.listaPorff(fechaInicio);
        return new ResponseEntity<>(curso, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurso(@PathVariable Long id){
        cursoService.deleteById(id);
        return  new ResponseEntity<>(new Mensaje("Curso eliminado"),HttpStatus.OK);
    }

    @PostMapping("/agregarcliente/{idCliente}/{idCurso}")
    public ResponseEntity<?> agregar(@PathVariable Long idCliente, @PathVariable Long idCurso){
        return new ResponseEntity<>( cursoService.agregarClientesalCurso(idCliente,idCurso), HttpStatus.OK);
    }

    @GetMapping("/allCursosbyfechamax")
    public ResponseEntity<List<CursoTaller>> listAllCursosfechamax(){
        List<CursoTaller> curso = cursoService.listarByfechamaxima();
        return new ResponseEntity<List<CursoTaller>>(curso, HttpStatus.OK);
    }

    @GetMapping("/allBylistaclientes/{id}")
    public ResponseEntity<Cliente_cursoResponse> listcursobyclientecurso(@PathVariable Long id) {
        Cliente_cursoResponse cc = cursoService.listarcursobyIdCursoutu(id);
        return new ResponseEntity<>(cc, HttpStatus.OK);
    }



}
