package com.Biblioteca.Repository.Computo;

import com.Biblioteca.Models.Servicio.CentroComputo.InventarioComputo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventarioComputoRepository extends JpaRepository<InventarioComputo, Long> {

    Optional<InventarioComputo> findByNumero(Long numero);

    List<InventarioComputo> findAllByEstado(Boolean estado);
}
