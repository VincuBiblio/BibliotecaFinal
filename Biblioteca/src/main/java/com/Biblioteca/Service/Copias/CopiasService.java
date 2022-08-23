package com.Biblioteca.Service.Copias;


import com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes.CopiasClienteRequest;
import com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes.CopiasClienteResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Servicio.CopiasImpresiones.CopiasCliente;
import com.Biblioteca.Models.Servicio.CopiasImpresiones.CopiasImpresiones;
import com.Biblioteca.Repository.CopiasImpresiones.CopiasClientesRepository;
import com.Biblioteca.Repository.CopiasImpresiones.CopiasImpresionesRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class CopiasService {

    @Autowired
    private CopiasImpresionesRepository copiasImpresionesRepository;

    @Autowired
    private CopiasClientesRepository copiasClientesRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    @Transactional
    public CopiasClienteResponse registroCopiasCliente(CopiasClienteRequest request){

        Optional<Cliente> cliente = clienteRepository.findById(request.getIdCliente());
        if (cliente.isPresent()){
            CopiasImpresiones newCopias= new CopiasImpresiones();
            newCopias.setPagBlanco(request.getPagBlanco());
            newCopias.setPagColor(request.getPagColor());
            newCopias.setPagTotal(request.getPagColor()+ request.getPagBlanco());

                CopiasImpresiones copias=copiasImpresionesRepository.save(newCopias);
               if(copias != null){
                   Optional<CopiasImpresiones> copiasImpres= copiasImpresionesRepository.findById(copias.getId());
                   if(copiasImpres.isPresent()){
                       CopiasCliente newCopiasCliente = new CopiasCliente();
                       newCopiasCliente.setDia((long)request.getFecha().getDate()+1);
                       newCopiasCliente.setMes((long)request.getFecha().getMonth()+1);
                       newCopiasCliente.setAnio((long) request.getFecha().getYear()+1900);
                       newCopiasCliente.setCliente(cliente.get());
                       newCopiasCliente.setCopias(copias);
                       try{
                           copiasClientesRepository.save(newCopiasCliente);
                           return new CopiasClienteResponse(request.getFecha(), cliente.get().getId(),
                                   copias.getId(), copias.getPagBlanco(), copias.getPagColor(), copias.getPagTotal());
                       }catch (Exception e){
                           throw new BadRequestException("No se guardó el cliente que ocupó servicio copias tbl copias_cliente" +e);
                       }
                   }else{
                       throw new BadRequestException("No existe las copias con id" +copias.getId());
                   }
               }


        }else{
            throw new BadRequestException("No existe un cliente con id" +request.getIdCliente());
        }

        return null;
    }
}
