package com.Biblioteca.Controller.Computo;

import com.Biblioteca.DTO.Computo.ComputoClienteRequest;
import com.Biblioteca.DTO.Computo.InventarioRequest;
import com.Biblioteca.DTO.Computo.InventarioResponse;
import com.Biblioteca.DTO.Ubicacion.BarrioRequest;
import com.Biblioteca.DTO.Ubicacion.BarrioResponse;
import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import com.Biblioteca.Service.Computo.ComputoClienteService;
import com.Biblioteca.Service.Computo.ComputoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/inventario/computo")
public class InventarioComputoController {

    @Autowired
    private ComputoService service;

    @Autowired
    private ComputoClienteService computoClienteService;

    @PostMapping("/registrocomputador")
    public ResponseEntity<?> registroComputador(@RequestBody InventarioRequest request){
        return new ResponseEntity<>(service.registrarInventario(request), HttpStatus.OK);
    }

    @PutMapping("/actualizarcomputador")
    public ResponseEntity<?> actualizarComputador(@RequestBody InventarioRequest request){
        return new ResponseEntity<>(service.actualizarInventario(request), HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<InventarioResponse>> all(){
        List<InventarioResponse> allInventario = service.listAllInventario();
        return new ResponseEntity<>(allInventario, HttpStatus.OK);
    }

    @GetMapping("/all/estado/{estado}")
    public ResponseEntity<List<InventarioResponse>> allByEstado(@PathVariable Boolean estado){
        List<InventarioResponse> allInventario = service.listAllInventarioByEstado(estado);
        return new ResponseEntity<>(allInventario, HttpStatus.OK);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<InventarioResponse> inventarioById(@PathVariable Long id){
        InventarioResponse response = service.inventarioById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<InventarioResponse> inventarioByNUmero(@PathVariable Long numero){
        InventarioResponse response = service.inventarioByNumero(numero);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/registrocomputadorcliente")
    public ResponseEntity<?> registroComputadorCliente(@RequestBody ComputoClienteRequest request){
        return new ResponseEntity<>(computoClienteService.registroComputoCliente(request), HttpStatus.OK);
    }

}
