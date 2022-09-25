package com.Biblioteca.Repository.Reporte;

import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportecomputoRepository extends JpaRepository<ComputoCliente, Long> {




    @Query(value = "select p.cedula as cedula,p.nombres as nombres, p.apellidos as apellidos, cl.genero as genero, p.telefono as telefono, p.email as email,  cocli.dia,cocli.mes, cocli.anio\n" +
            "from persona p, cliente cl, inventario_computo ci, computo_cliente cocli\n" +
            "where cocli.mes = :mes and cocli.anio  = :anio \n" +
            "and cl.persona_id = p.id and cocli.id_cliente = cl.id and cocli.id_inventario  = ci.id ", nativeQuery = true)
    List<DatosReporte> findAllByMesandAnio(Long mes, Long anio);

}