package com.Biblioteca.Service.Computo;

import com.Biblioteca.DTO.Computo.ComputoClienteRequest;
import com.Biblioteca.DTO.Computo.ComputoClienteResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Persona.Persona;
import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import com.Biblioteca.Models.Servicio.CentroComputo.InventarioComputo;
import com.Biblioteca.Repository.Computo.ComputoClienteRepository;
import com.Biblioteca.Repository.Computo.InventarioComputoRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import com.Biblioteca.Repository.Persona.PersonaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ComputoClienteService {

    @Autowired
    private InventarioComputoRepository inventarioComputoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

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


    @Transactional
    public List<ComputoClienteResponse> listAllPrestamos(){
        List<ComputoCliente> listaCC= computoClienteRepository.findAll();
        return listaCC.stream().map(computoCliente -> {
            ComputoClienteResponse response = new ComputoClienteResponse();
            Optional<Cliente> cliente = clienteRepository.findById(computoCliente.getCliente().getId());
            if (cliente.isPresent()) {
                Optional<Persona> persona = personaRepository.findById(cliente.get().getId());
                if(persona.isPresent()){
                        Optional<InventarioComputo> inventarioComputo = inventarioComputoRepository.findById(computoCliente.getInventario().getId());
                        if(inventarioComputo.isPresent()){
                            Persona p= persona.get();
                            Cliente c = cliente.get();
                            InventarioComputo i = inventarioComputo.get();
                            response.setIdPrestamo(computoCliente.getId());
                            response.setIdPersona(p.getId());
                            response.setIdCliente(c.getId());
                            response.setDescripcion(computoCliente.getDescripcion());
                            response.setHoraInicio(computoCliente.getHoraInicio());
                            response.setHoraFin(computoCliente.getHoraFin());
                            response.setCedula(p.getCedula());
                            response.setApellidos(p.getApellidos());
                            response.setNombres(p.getNombres());
                            response.setFechaNacimiento(c.getFechaNacimiento());
                            response.setEdad(c.getEdad());
                            response.setGenero(c.getGenero());
                            response.setTelefono(p.getTelefono());
                            response.setEmail(p.getEmail());
                            response.setEstadoCivil(c.getEstadoCivil());
                            response.setDiscapacidad(c.getDiscapacidad());
                            response.setNombreResponsable(c.getNombreResponsable());
                            response.setTelefonoResponsbale(c.getTelefonoResponsbale());
                            response.setBarrio(c.getUbicacion().getBarrio().getBarrio());
                            response.setParroquia(c.getUbicacion().getParroquia().getParroquia());
                            response.setCanton(c.getUbicacion().getCanton().getCanton());
                            response.setProvincia(c.getUbicacion().getProvincia().getProvincia());
                            response.setIdComputador(computoCliente.getInventario().getId());
                            response.setNumero(i.getNumero());
                            response.setRam(i.getRam());
                            response.setDiscoDuro(i.getDiscoDuro());
                            response.setProcesador(i.getProcesador());
                            response.setEstado(i.getEstado());
                            return response;
                        }else{
                            throw new BadRequestException("No existe computador con id " + computoCliente.getInventario().getId());
                        }
                }else{
                    throw new BadRequestException("No existe persona con id asociada al cliente" + cliente.get().getId());
                }
            }else{
                throw new BadRequestException("No existe cliente con id" + computoCliente.getCliente().getId());
            }
        }).collect(Collectors.toList());
    }

}
