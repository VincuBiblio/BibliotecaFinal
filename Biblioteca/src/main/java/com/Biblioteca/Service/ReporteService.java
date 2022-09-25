package com.Biblioteca.Service;

import com.Biblioteca.DTO.Reporte.Reportesd;
import com.Biblioteca.Repository.CopiasImpresiones.CopiasClientesRepository;
import com.Biblioteca.Repository.CursoTaller.TallerRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import com.Biblioteca.Repository.Reporte.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReporteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TallerRepository tallerRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CopiasClientesRepository copiasClientesRepository;

    @Autowired
    private ReporteRepository reporteRepository;
    @Autowired
    private ReportecomputoRepository reportecomputoRepository;
    @Autowired
    private ReporteLibroRepository reportelibroRepository;

    @Autowired
    private ReporteporTallerRepository reporteporTallerRepository;



    @Transactional
    public List<Reportesd> listarbycopias(Long mes, Long anio) {
        List<DatosReporte> datos1=reporteRepository.findAllByMesandAnio(mes, anio);
        return datos1.stream().map(clienteRequest -> {
            Reportesd listaa=new Reportesd();
            listaa.setCedula(clienteRequest.getCedula());
            listaa.setNombres(clienteRequest.getNombres());
            listaa.setApellidos(clienteRequest.getApellidos());
            listaa.setGenero(clienteRequest.getGenero());
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listaa.setFecha(clienteRequest.getAnio()+"-"+fmt.format("%02d",clienteRequest.getMes())+"-"+fmt2.format("%02d",clienteRequest.getDia()));

            listaa.setCopias(Long.valueOf(1));
            return listaa;
        }).collect(Collectors.toList());
    }
    @Transactional
    public List<Reportesd> listarbycomputo(Long mes, Long anio) {
        List<DatosReporte> datos2 = reportecomputoRepository.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setFecha(clienteRequest2.getAnio()+"-"+fmt.format("%02d",clienteRequest2.getMes())+"-"+fmt2.format("%02d",clienteRequest2.getDia()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setComputo(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<Reportesd> listarbyprestamolibros(Long mes, Long anio) {
        List<DatosReporte> datos3 = reportelibroRepository.findAllByMesandAnio(mes, anio);
        return datos3.stream().map(clienteRequest3 -> {
            Reportesd listac = new Reportesd();
            listac.setCedula(clienteRequest3.getCedula());
            listac.setNombres(clienteRequest3.getNombres());
            listac.setApellidos(clienteRequest3.getApellidos());
            listac.setGenero(clienteRequest3.getGenero());
            listac.setEmail(clienteRequest3.getEmail());
            listac.setTelefono(clienteRequest3.getTelefono());
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listac.setFecha(clienteRequest3.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest3.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest3.getDia_prestamo()));
            listac.setRepositorio(Long.valueOf(1));
            return listac;
        }).collect(Collectors.toList());
    }




    @Transactional
    public List<Reportesd>  listartallerbyClientes(Long mes, Long anio) {

        Formatter fmt = new Formatter();
        fmt.format("%02d",mes);
        String fec=anio+"-"+fmt;
        List<DatosReporte> datos4 = reporteporTallerRepository.findAllByFecha_inicio(fec);
        System.out.println(fec);
        return datos4.stream().map(clienteRequest4 -> {
            Reportesd listad = new Reportesd();
            listad.setNombretaller(clienteRequest4.getNombre());
            listad.setTalleres(Long.valueOf(1));
            listad.setFecha(clienteRequest4.getFecha_inicio()+" a "+clienteRequest4.getFecha_fin());

            listad.setCedula(clienteRequest4.getCedula());
            listad.setNombres(clienteRequest4.getNombres());
            listad.setApellidos(clienteRequest4.getApellidos());
            listad.setGenero(clienteRequest4.getGenero());
            return listad;
        }).collect(Collectors.toList());

    }



    @Transactional
    public List<Reportesd> listartodo3(Long mes, Long anio) {
        List<Reportesd> list = new ArrayList<>();
        list.addAll((Collection<? extends Reportesd>) listarbycopias(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarbycomputo(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarbyprestamolibros(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listartallerbyClientes(mes, anio));
        return list;

    }



}
