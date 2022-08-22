package com.Biblioteca.Service;

import com.Biblioteca.DTO.CursoTaller.TallerFecha;
import com.Biblioteca.DTO.CursoTaller.TallerRequest;
import com.Biblioteca.DTO.CursoTaller.TallerResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.CursoTaller.CursoTaller;
import com.Biblioteca.Models.CursoTaller.Taller.Taller;
import com.Biblioteca.Repository.CursoTaller.CursoTallerRepository;
import com.Biblioteca.Repository.CursoTaller.TallerRepository;
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
public class TallerService {
    @Autowired
    private TallerRepository tallerRepository;
    @Autowired
    private CursoTallerRepository cursotallerRepository;

    @Transactional
    public TallerResponse registrarTaller(TallerRequest tallerRequest) {
        Optional<CursoTaller> optional1 = cursotallerRepository.findByNombreAndFechaInicio(tallerRequest.getNombre(),tallerRequest.getFechaInicio());
        if (!optional1.isPresent()) {
            CursoTaller newcursoTaller = new CursoTaller();
            newcursoTaller.setNombre(tallerRequest.getNombre());
            newcursoTaller.setLugar(tallerRequest.getLugar());
            newcursoTaller.setDescripcion(tallerRequest.getDescripcion());
            newcursoTaller.setObservaciones(tallerRequest.getObservaciones());
            newcursoTaller.setResponsable(tallerRequest.getResponsable());
            newcursoTaller.setFechaInicio(tallerRequest.getFechaInicio());
            newcursoTaller.setFechaFin(tallerRequest.getFechaFin());

            CursoTaller cursoTaller = cursotallerRepository.save(newcursoTaller);
            if (cursoTaller != null) {
                guardarTaller(cursoTaller.getNombre(), cursoTaller.getFechaInicio());
                Optional<Taller> taller = tallerRepository.findByCursoTaller(cursoTaller);

                return new TallerResponse(cursoTaller.getId(), cursoTaller.getNombre(), cursoTaller.getLugar(),
                        cursoTaller.getDescripcion(), cursoTaller.getObservaciones(), cursoTaller.getResponsable(),
                        cursoTaller.getFechaInicio(), cursoTaller.getFechaFin());
            } else {
                throw new BadRequestException("No se pudo guardar el taller" + cursoTaller.getNombre());
            }

        } else {
            throw new BadRequestException("ya registrado");
        }
    }

        private boolean guardarTaller(String nombre, Date fechainicio) {
        Optional<CursoTaller> optional2 = cursotallerRepository.findByNombreAndFechaInicio(nombre, fechainicio);
        if (optional2.isPresent()) {
            CursoTaller ct = optional2.get();
            Taller nuevo = new Taller();
            nuevo.setCursoTaller(ct);
            Taller talleres = tallerRepository.save(nuevo);
            if (talleres != null) {
                return true;
            } else {
                throw new BadRequestException("Taller no registrado");
            }
        } else {
            throw new BadRequestException("ya existe un taller con el nombre" + nombre + "y para la fecha" + fechainicio);
        }
    }


    public boolean actualizartalleres(TallerRequest tallerrequest) {
        Optional<CursoTaller> optionalc = cursotallerRepository.findById(tallerrequest.getId());
        if (optionalc.isPresent()) {
            optionalc.get().setNombre(tallerrequest.getNombre());
            optionalc.get().setLugar(tallerrequest.getLugar());
            optionalc.get().setDescripcion(tallerrequest.getDescripcion());
            optionalc.get().setObservaciones(tallerrequest.getObservaciones());
            optionalc.get().setResponsable(tallerrequest.getResponsable());
            optionalc.get().setFechaInicio(tallerrequest.getFechaInicio());
            optionalc.get().setFechaFin(tallerrequest.getFechaFin());
            try {
                CursoTaller ct = cursotallerRepository.save(optionalc.get());
                if (ct != null) {
                    guardaractualizacion(ct);
                } else {
                    throw new BadRequestException("NO SE ACTUALIZO EL TALLER");
                }
            } catch (Exception ex) {
                throw new BadRequestException("No ACTUALIZADO" + ex);
            }
        } else {
            throw new BadRequestException("NO EXISTE EL TALLER CON ID " + tallerrequest.getId());
        }
        return false;
    }

    private boolean guardaractualizacion(CursoTaller cursoTaller) {
        Optional<Taller> optionalc = tallerRepository.findByCursoTaller(cursoTaller);
        ;
        if (optionalc.isPresent()) {
            try {
                Taller c = tallerRepository.save(optionalc.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("NO SE ACTUALIZO LA TABLA CURSO/ TALLER" + ex);
            }
        }
        throw new BadRequestException("NO EXISTE EL TALLER");
    }

    public List<TallerResponse> listAllTalleres() {
        List<Taller> taller = tallerRepository.findAll();
        return taller.stream().map(cRequest -> {
            TallerResponse tr = new TallerResponse();
            tr.setId(cRequest.getCursoTaller().getId());
            tr.setNombre(cRequest.getCursoTaller().getNombre());
            tr.setLugar(cRequest.getCursoTaller().getLugar());
            tr.setDescripcion(cRequest.getCursoTaller().getDescripcion());
            tr.setObservaciones(cRequest.getCursoTaller().getDescripcion());
            tr.setResponsable(cRequest.getCursoTaller().getResponsable());
            tr.setFechaInicio(cRequest.getCursoTaller().getFechaInicio());
            tr.setFechaFin(cRequest.getCursoTaller().getFechaFin());
            tr.setIdTaller(cRequest.getId());
            return tr;
        }).collect(Collectors.toList());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<TallerFecha> listaPorft(Date fechaInicio){
        return tallerRepository.findDistinctByFechaInicio(fechaInicio);
    }

}