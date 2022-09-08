package com.Biblioteca.Repository.Libros;

import com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes.ListaLibrosPrestamo;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroClienteRepository extends JpaRepository<PrestamoLibroCliente,Long> {

    @Query(value = "select p.nombres ,p.apellidos, p.cedula,p.telefono , pr.id,pr.dia_prestamo, pr.mes_prestamo,pr.anio_prestamo,pr.dia_dev,pr.mes_dev,pr.anio_dev,l.codigo_libro, l.estado\n" +
            "from prestamo_libros l , prestamolibros_cliente pr , persona p , cliente c\n" +
            "where l.estado= :estado and l.id=pr.id_prestamo and c.persona_id=p.id and c.id=pr.id_cliente", nativeQuery = true)
    List<ListaLibrosPrestamo> findAllByEstado(Boolean estado);

}
