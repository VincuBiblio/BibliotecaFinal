package com.Biblioteca.Service;


import com.Biblioteca.DTO.Evento.EventoRequest;
import com.Biblioteca.DTO.Evento.EventoResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Evento.Evento;
import com.Biblioteca.Models.Persona.Usuario;
import com.Biblioteca.Repository.Evento.EventoRepository;
import com.Biblioteca.Repository.Persona.PersonaRepository;
import com.Biblioteca.Repository.Persona.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public boolean registroevento(EventoRequest eventoRequest) {
        Usuario user= getIdUsuario(eventoRequest.getUsuarioid());
        saveEvento(eventoRequest, user);
        return true;
    }
    public Usuario getIdUsuario(Long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new BadRequestException("No existe el usuario con ese id");
    }
    public void saveEvento(EventoRequest eventoRequest, Usuario user) {
        Evento evento = new Evento();
        evento.setDescripcion(eventoRequest.getDescripcion());
        evento.setFecha(eventoRequest.getFecha());
        evento.setActividades(eventoRequest.getActividades());
        evento.setObservaciones(eventoRequest.getObservaciones());
        evento.setUsuario(user);
        try {
            Evento  saved = eventoRepository.save(evento);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadRequestException("NO SE REGISTRO EL EVENTO" + ex);
        }
    }

    @Transactional
    public boolean actualizardatosevento(EventoRequest eventoRequest){
        Optional<Evento> evento= eventoRepository.findById(eventoRequest.getId());
//        Optional<Usuario> optionaluser = usuarioRepository.findById(eventoRequest.getUsuario_id());
        if(evento.isPresent()){
            evento.get().setDescripcion(eventoRequest.getDescripcion());
            evento.get().setFecha(eventoRequest.getFecha());
            evento.get().setActividades(eventoRequest.getActividades());
            evento.get().setObservaciones(eventoRequest.getObservaciones());
            evento.get().setNumParticipantes(eventoRequest.getNumParticipantes());
            evento.get().setDocumento(eventoRequest.getDocumento());
            try{
                eventoRepository.save(evento.get());
                return true;
            }catch (Exception ex) {
                throw new BadRequestException("No se actualizo" + ex);
            }
        } else {
            throw new BadRequestException("No existe un evento con id "+eventoRequest.getId() );
        }
    }


    @Transactional
    public boolean insertarnumeroparticipantes(EventoRequest eventoRequest){
        Optional<Evento> evento= eventoRepository.findById(eventoRequest.getId());
        if(evento.isPresent()){
            evento.get().setNumParticipantes(eventoRequest.getNumParticipantes());
            evento.get().setObservaciones(eventoRequest.getObservaciones());
            evento.get().setDocumento(eventoRequest.getDocumento());
            try{
                eventoRepository.save(evento.get());
                return true;
            }catch (Exception ex) {
                throw new BadRequestException("No se agrego el numero estimado de participantes" + ex);
            }
        }else {
            throw new BadRequestException("No existe un evento con id "+eventoRequest.getId() );
        }
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<EventoResponse> listAllEventos() {
        List<Evento> ev = eventoRepository.findAll();
        return ev.stream().map(cRequest -> {
            EventoResponse er = new EventoResponse();
            er.setUsuarioid(cRequest.getUsuario().getId());
            er.setId(cRequest.getId());
            er.setDescripcion(cRequest.getDescripcion());
            er.setActividades(cRequest.getActividades());
            er.setObservaciones(cRequest.getObservaciones());
            er.setNumParticipantes(cRequest.getNumParticipantes());
            er.setFecha(cRequest.getFecha());
            er.setDocumento(cRequest.getDocumento());
            return er;
        }).collect(Collectors.toList());
    }
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<EventoResponse> listAllEventossinparticipantes() {
        List<Evento> ev = eventoRepository.findAll();
        return ev.stream().filter(value->
                value.getNumParticipantes()==null
        ).map(cRequest -> {
            EventoResponse er = new EventoResponse();
            er.setUsuarioid(cRequest.getUsuario().getId());
            er.setId(cRequest.getId());
            er.setDescripcion(cRequest.getDescripcion());
            er.setActividades(cRequest.getActividades());
            er.setObservaciones(cRequest.getObservaciones());
            er.setNumParticipantes(cRequest.getNumParticipantes());
            er.setFecha(cRequest.getFecha());
            er.setDocumento(cRequest.getDocumento());
            return er;
        }).collect(Collectors.toList());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<EventoResponse> listAllEventosconparticipantes() {
        List<Evento> ev = eventoRepository.findAll();
        return ev.stream().filter(value->
                value.getNumParticipantes()!=null
        ).map(cRequest -> {
            EventoResponse er = new EventoResponse();
            er.setUsuarioid(cRequest.getUsuario().getId());
            er.setId(cRequest.getId());
            er.setDescripcion(cRequest.getDescripcion());
            er.setActividades(cRequest.getActividades());
            er.setObservaciones(cRequest.getObservaciones());
            er.setNumParticipantes(cRequest.getNumParticipantes());
            er.setFecha(cRequest.getFecha());
            er.setDocumento(cRequest.getDocumento());
            return er;
        }).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        Optional<Evento> optional = eventoRepository.findById(id);
        if (optional.isPresent()) {
            eventoRepository.deleteById(id);
        } else {
            throw new BadRequestException("El evento con el id " + id + ", no existe");
        }
    }
}