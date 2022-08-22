package com.Biblioteca.Service;

import com.Biblioteca.DTO.CursoTaller.CursoFecha;
import com.Biblioteca.DTO.CursoTaller.CursoRequest;
import com.Biblioteca.DTO.CursoTaller.CursoResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.CursoTaller.Curso.Curso;
import com.Biblioteca.Models.CursoTaller.CursoTaller;
import com.Biblioteca.Repository.CursoTaller.CursoRepository;
import com.Biblioteca.Repository.CursoTaller.CursoTallerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private CursoTallerRepository cursotallerRepository;

    @Transactional
    public CursoResponse registrarCurso(CursoRequest cursoRequest) {
        Optional<CursoTaller> optional1 = cursotallerRepository.findByNombreAndFechaInicio(cursoRequest.getNombre(), cursoRequest.getFechaInicio());
        if (!optional1.isPresent()) {
            CursoTaller newcursoTaller = new CursoTaller();
            newcursoTaller.setNombre(cursoRequest.getNombre());
            newcursoTaller.setLugar(cursoRequest.getLugar());
            newcursoTaller.setDescripcion(cursoRequest.getDescripcion());
            newcursoTaller.setObservaciones(cursoRequest.getObservaciones());
            newcursoTaller.setResponsable(cursoRequest.getResponsable());
            newcursoTaller.setFechaInicio(cursoRequest.getFechaInicio());
            newcursoTaller.setFechaFin(cursoRequest.getFechaFin());

            CursoTaller cursoTaller = cursotallerRepository.save(newcursoTaller);
            if (cursoTaller != null) {
                guardarCurso(cursoTaller.getNombre(), cursoTaller.getFechaInicio(), cursoRequest.getActividades(), cursoRequest.getMateriales(), cursoRequest.getNumParticipantes());
                Optional<Curso> curso = cursoRepository.findByCursoTaller(cursoTaller);

                return new CursoResponse(cursoTaller.getId(), cursoTaller.getNombre(), cursoTaller.getLugar(),
                        cursoTaller.getDescripcion(), cursoTaller.getObservaciones(), cursoTaller.getResponsable(),
                        cursoTaller.getFechaInicio(), cursoTaller.getFechaFin(), curso.get().getActividades(),
                        curso.get().getMateriales(), curso.get().getNumParticipantes());
            } else {
                throw new BadRequestException("No se pudo guardar el curso" + cursoTaller.getNombre());
            }

        } else {
            throw new BadRequestException("ya registrado");
        }
    }

    private boolean guardarCurso(String nombre, Date fechainicio, String actividades, String materiales, Long numParticipantes) {
        Optional<CursoTaller> optional2 = cursotallerRepository.findByNombreAndFechaInicio(nombre, fechainicio);
        if (optional2.isPresent()) {
            CursoTaller ct = optional2.get();
            Curso nuevo = new Curso();
            nuevo.setActividades(actividades);
            nuevo.setMateriales(materiales);
            nuevo.setNumParticipantes(numParticipantes);
            nuevo.setCursoTaller(ct);
            Curso cursos = cursoRepository.save(nuevo);
            if (cursos != null) {
                return true;
            } else {
                throw new BadRequestException("Curso no registrado");
            }
        } else {
            throw new BadRequestException("ya existe un curso con el nombre" + nombre + "y para la fecha" + fechainicio);
        }
    }


    public boolean actualizarcursos(CursoRequest cursorequest) {
        Optional<CursoTaller> optionalc = cursotallerRepository.findById(cursorequest.getId());
        if (optionalc.isPresent()) {
            optionalc.get().setNombre(cursorequest.getNombre());
            optionalc.get().setLugar(cursorequest.getLugar());
            optionalc.get().setDescripcion(cursorequest.getDescripcion());
            optionalc.get().setObservaciones(cursorequest.getObservaciones());
            optionalc.get().setResponsable(cursorequest.getResponsable());
            optionalc.get().setFechaInicio(cursorequest.getFechaInicio());
            optionalc.get().setFechaFin(cursorequest.getFechaFin());
            try {
                CursoTaller ct = cursotallerRepository.save(optionalc.get());
                if (ct != null) {
                    guardaractualizacion(ct, cursorequest.getActividades(), cursorequest.getMateriales(), cursorequest.getNumParticipantes());
                } else {
                    throw new BadRequestException("NO SE ACTUALIZO EL CURSO");
                }
            } catch (Exception ex) {
                throw new BadRequestException("No ACTUALIZADO" + ex);
            }
        } else {
            throw new BadRequestException("NO EXISTE EL CURSO CON ID " + cursorequest.getId());
        }
        return false;
    }

    private boolean guardaractualizacion(CursoTaller cursoTaller, String actividades, String materiales, Long numparticipantes) {
        Optional<Curso> optionalc = cursoRepository.findByCursoTaller(cursoTaller);
        ;
        if (optionalc.isPresent()) {
            optionalc.get().setActividades(actividades);
            optionalc.get().setMateriales(materiales);
            optionalc.get().setNumParticipantes(numparticipantes);
            try {
                Curso c = cursoRepository.save(optionalc.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("NO SE ACTUALIZO LA TABLA CURSO/ TALLER" + ex);
            }
        }
        throw new BadRequestException("NO EXISTE EL CURSO");
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CursoResponse> listAllCursos() {
        List<Curso> curso = cursoRepository.findAll();
        return curso.stream().map(cRequest -> {
            CursoResponse cr = new CursoResponse();
            cr.setId(cRequest.getCursoTaller().getId());
            cr.setNombre(cRequest.getCursoTaller().getNombre());
            cr.setLugar(cRequest.getCursoTaller().getLugar());
            cr.setDescripcion(cRequest.getCursoTaller().getDescripcion());
            cr.setObservaciones(cRequest.getCursoTaller().getDescripcion());
            cr.setResponsable(cRequest.getCursoTaller().getResponsable());
            cr.setFechaInicio(cRequest.getCursoTaller().getFechaInicio());
            cr.setFechaFin(cRequest.getCursoTaller().getFechaFin());
            cr.setIdCurso(cRequest.getId());
            cr.setActividades(cRequest.getActividades());
            cr.setMateriales(cRequest.getMateriales());
            cr.setNumParticipantes(cRequest.getNumParticipantes());
            return cr;
        }).collect(Collectors.toList());
    }
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public CursoResponse listarcursobyId(Long id) {
        CursoResponse cr = new CursoResponse();
        Optional<CursoTaller> ct = cursotallerRepository.findById(id);
        if (ct.isPresent()) {
            Optional<Curso> curso = cursoRepository.findByCursoTaller(ct.get());
            if (curso.isPresent()) {
                cr.setId(ct.get().getId());
                cr.setNombre(ct.get().getNombre());
                cr.setLugar(ct.get().getLugar());
                cr.setDescripcion(ct.get().getDescripcion());
                cr.setObservaciones(ct.get().getDescripcion());
                cr.setResponsable(ct.get().getResponsable());
                cr.setFechaInicio(ct.get().getFechaInicio());
                cr.setFechaFin(ct.get().getFechaFin());
                cr.setIdCurso(curso.get().getId());
                cr.setActividades(curso.get().getActividades());
                cr.setMateriales(curso.get().getMateriales());
                cr.setNumParticipantes(curso.get().getNumParticipantes());
                return cr;
            } else {
                throw new BadRequestException("NO EXISTE EL CURSO CON ID " + id);
            }
        } else {
            throw new BadRequestException("NO EXISTE UN CURSO DESDE LA TABLA CURSO/TALLER");
        }
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CursoFecha> listaPorff(Date fechaInicio){
        return cursoRepository.findByFechaInicio(fechaInicio);
    }

    public void deleteById(Long id){
        Optional<Curso> optional = cursoRepository.findById(id);
        if(!optional.isPresent()){
            throw new BadRequestException("El curso con el id " + id + ", no existe");
        }
        cursoRepository.deleteById(id);
    }
}