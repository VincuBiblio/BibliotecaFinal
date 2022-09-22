package com.Biblioteca.Service;

import com.Biblioteca.DTO.Reporte.Reportesd;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Repository.CopiasImpresiones.CopiasClientesRepository;
import com.Biblioteca.Repository.CursoTaller.TallerRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
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



    public BigInteger contarclienteencopias(Long id, Long mes, Long anio) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT count(*) FROM copias_cliente cc " +
                " where cc.id_cliente= ? and cc.mes=? and cc.anio=?");
        nativeQuery.setParameter(1, id);
        nativeQuery.setParameter(2, mes);
        nativeQuery.setParameter(3, anio);
        return (BigInteger) nativeQuery.getSingleResult();
    }
    public BigInteger contarclientelibros(Long id, Long mes, Long anio) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT count(*) FROM prestamolibros_cliente pl " +
                " where pl.id_cliente= ? and pl.mes_prestamo=? and pl.anio_prestamo=?");
        nativeQuery.setParameter(1, id);
        nativeQuery.setParameter(2, mes);
        nativeQuery.setParameter(3, anio);
        return (BigInteger) nativeQuery.getSingleResult();
    }
    public BigInteger contarclientecomputo(Long id, Long mes, Long anio) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT count(*) FROM computo_cliente ccl " +
                " where ccl.id_cliente= ? and ccl.mes=? and ccl.anio=?");
        nativeQuery.setParameter(1, id);
        nativeQuery.setParameter(2, mes);
        nativeQuery.setParameter(3, anio);
        return (BigInteger) nativeQuery.getSingleResult();
    }
    public BigInteger buscarclientectaller(Long id) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT count(*) FROM taller_clientes tcl " +
                " where tcl.cliente_id= ?");
        nativeQuery.setParameter(1, id);
        return (BigInteger) nativeQuery.getSingleResult();
    }

    public BigInteger buscaridtaller(Long id) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT ct.taller_id FROM taller_clientes ct" +
                " where ct.cliente_id= ?");
        nativeQuery.setParameter(1, id);
        return (BigInteger) nativeQuery.getSingleResult();
    }

    public String buscarnombretaller(Long idtaller) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT ct.nombre FROM curso_taller ct join taller t on t.curso_taller_id=ct.id where t.id=?");
        nativeQuery.setParameter(1, idtaller);
        return (String) nativeQuery.getSingleResult();
    }
    public Date buscarfechatallerinicio(Long idtaller) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT ct.fecha_inicio FROM curso_taller ct join taller t on t.curso_taller_id=ct.id where t.id=?");
        nativeQuery.setParameter(1, idtaller);
        return (Date) nativeQuery.getSingleResult();
    }
    public Date buscarfechatallerfin(Long idtaller) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT ct.fecha_fin FROM curso_taller ct join taller t on t.curso_taller_id=ct.id where t.id=?");
        nativeQuery.setParameter(1, idtaller);
        return (Date) nativeQuery.getSingleResult();
    }


    public List<Reportesd> listAllbymes(Long mes, Long anio){

        List<Cliente> cliente = clienteRepository.findAll();
        return cliente.stream().map(clienteRequest->{
            Reportesd pcr = new Reportesd();
            String codig="B.M. EL VALLE";
            String verificables="Incluido en el informe mensual";
            pcr.setCodigo(codig);
            pcr.setVerificables(verificables);

            pcr.setIdcliente(clienteRequest.getId());
            pcr.setCedula(clienteRequest.getPersona().getCedula());
            pcr.setNombres(clienteRequest.getPersona().getNombres());
            pcr.setApellidos(clienteRequest.getPersona().getApellidos());
            pcr.setFechaNacimiento(clienteRequest.getFechaNacimiento());
            pcr.setEdad(clienteRequest.getEdad());
            pcr.setGenero(clienteRequest.getGenero());
            pcr.setTelefono(clienteRequest.getPersona().getTelefono());
            pcr.setEmail(clienteRequest.getPersona().getEmail());
            pcr.setEstadoCivil(clienteRequest.getEstadoCivil());
            pcr.setDiscapacidad(clienteRequest.getDiscapacidad());
            pcr.setBarrio(clienteRequest.getUbicacion().getBarrio().getBarrio());
            pcr.setParroquia(clienteRequest.getUbicacion().getParroquia().getParroquia());
            pcr.setCanton(clienteRequest.getUbicacion().getCanton().getCanton());
            pcr.setProvincia(clienteRequest.getUbicacion().getProvincia().getProvincia());

                if(contarclienteencopias(clienteRequest.getId(),mes,anio)!=null){
                    BigInteger varnro;
                   varnro=contarclienteencopias(clienteRequest.getId(),mes,anio);
                   pcr.setCopias(varnro.longValue()); 

                }else{
                    pcr.setCopias(null);
                }
                    if(contarclientelibros(clienteRequest.getId(),mes, anio)!=null){
                        BigInteger varnro2;
                        varnro2=contarclientelibros(clienteRequest.getId(),mes, anio);
                        pcr.setRepositorio(varnro2.longValue());
                    }else{
                        pcr.setRepositorio(null);
                    }
                        if(contarclientecomputo(clienteRequest.getId(),mes,anio)!=null){
                            BigInteger varnro3;
                            varnro3=contarclientecomputo(clienteRequest.getId(),mes,anio);
                            pcr.setComputo(varnro3.longValue());
                        }else{
                            pcr.setComputo(null);
                        }
                            if(buscarclientectaller(clienteRequest.getId())!=null){
                                BigInteger varnro4;
                                varnro4=buscarclientectaller(clienteRequest.getId());
                                pcr.setTalleres(varnro4.longValue());

                                if(varnro4.longValue()>0) {
                                    BigInteger ll;
                                    ll = buscaridtaller(clienteRequest.getId());
                                    System.out.println("id taller " + ll.longValue());
                                    pcr.setIdtaller(ll.longValue());

                                    String nombretaller;
                                    nombretaller=buscarnombretaller(ll.longValue());
                                    pcr.setNombretaller(nombretaller);

                                    Date fecha1,fecha2;
                                    String fecha;
                                    fecha1=buscarfechatallerinicio(ll.longValue());
                                    fecha2=buscarfechatallerfin(ll.longValue());
                                    fecha= String.valueOf(fecha1)+" y "+String.valueOf(fecha2);
                                    pcr.setFecha(fecha);
                                }

                            }else{
                                pcr.setTalleres(null);
                            }

                        return pcr;
        }).collect(Collectors.toList()).stream().filter(value->
                value.getCopias().longValue()!=0 || value.getComputo().longValue()!=0 || value.getRepositorio().longValue()!=0).collect(Collectors.toList());

    }




}
