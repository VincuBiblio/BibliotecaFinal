package com.Biblioteca.Repository.Reporte;

import com.Biblioteca.Models.CursoTaller.Taller.Taller;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReporteporTallerRepository extends JpaRepository<Taller, Long> {

    @Query(value = "SELECT ct.fecha_fin, ct.fecha_inicio, ct.nombre, p.nombres,p.cedula, cli.genero " +
            "from curso_taller ct, taller t, persona p, cliente cli, taller_clientes tacli " +
            "where ct.id=t.curso_taller_id and tacli.taller_id=t.id and tacli.cliente_id=cli.id and cli.persona_id = p.id " +
            "and CAST(ct.fecha_inicio AS text) LIKE CONCAT(:fecha_inicio,'%') ", nativeQuery = true)
    List<DatosReporte> findAllByFecha_inicio(String fecha_inicio);


}
