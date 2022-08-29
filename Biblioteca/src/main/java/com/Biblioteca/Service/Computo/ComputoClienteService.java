package com.Biblioteca.Service.Computo;

import com.Biblioteca.DTO.Computo.ComputoClienteRequest;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import com.Biblioteca.Models.Servicio.CentroComputo.InventarioComputo;
import com.Biblioteca.Repository.Computo.ComputoClienteRepository;
import com.Biblioteca.Repository.Computo.InventarioComputoRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Slf4j
@Service
public class ComputoClienteService {

    @Autowired
    private InventarioComputoRepository inventarioComputoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComputoClienteRepository computoClienteRepository;


    @Transactional
    public boolean registroComputoCliente(ComputoClienteRequest request){
        Optional<Cliente> cliente = clienteRepository.findById(request.getIdCliente());
        if(cliente.isPresent()){
            Optional<InventarioComputo> inventarioComputo = inventarioComputoRepository.findById(request.getIdInventario());
            if(inventarioComputo.isPresent()){
                ComputoCliente newComputoCliente = new ComputoCliente();
                newComputoCliente.setDescripcion(request.getDescripcion());
                newComputoCliente.setHoraInicio(request.getHoraInicio());
                newComputoCliente.setHoraFin(request.getHoraFin());
                newComputoCliente.setCliente(cliente.get());
                newComputoCliente.setInventario(inventarioComputo.get());
                try{
                    computoClienteRepository.save(newComputoCliente);
                    return true;
                }catch (Exception ex) {
                    throw new BadRequestException("No se guardó el cliente/computo" + ex);
                }
            }else {
                throw new BadRequestException("No existe computador con id" + request.getIdInventario());
            }
        }else {
            throw new BadRequestException("No existe cliente con id" + request.getIdCliente());
        }
    }




}
