package com.Biblioteca.Repository.CopiasImpresiones;

import com.Biblioteca.Models.Servicio.CopiasImpresiones.CopiasImpresiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CopiasImpresionesRepository extends JpaRepository<CopiasImpresiones, Long> {


}
