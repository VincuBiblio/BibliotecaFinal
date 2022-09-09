package com.Biblioteca.Repository.Libros;

import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroClienteRepository extends JpaRepository<PrestamoLibroCliente,Long> {

    @Query(value = "select p.nombres as nombres,p.apellidos as apellidos, p.cedula as cedula,p.telefono as telefono, \n" +
            "pr.id as idprestamo, l.id as idlibro,\n" +
            "pr.dia_prestamo as diaprestamo, pr.mes_prestamo as mesprestamo,pr.anio_prestamo as anioprestamo,\n" +
            "pr.dia_dev as diadev,pr.observaciones_entrega as observacionEntrega, pr.mes_dev as mesdev,pr.anio_dev as aniodev,\n" +
            "l.codigo_libro as codigoLibro, l.estado as estado\n" +
            "from prestamo_libros l , prestamolibros_cliente pr , persona p , cliente c\n" +
            "where l.estado=true and l.id=pr.id_prestamo and c.persona_id=p.id and c.id=pr.id_cliente", nativeQuery = true)
    List<ListaLibrosPrestamo> findAllByEstado(Boolean estado);

}
