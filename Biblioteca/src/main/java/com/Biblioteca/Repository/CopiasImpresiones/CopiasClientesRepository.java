package com.Biblioteca.Repository.CopiasImpresiones;

import com.Biblioteca.Models.Servicio.CopiasImpresiones.CopiasCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CopiasClientesRepository extends JpaRepository<CopiasCliente, Long> {
    List<CopiasCliente> findByMesAndAnio(Long mes, Long anio);
}
