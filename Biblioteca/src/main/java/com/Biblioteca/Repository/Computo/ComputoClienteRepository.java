package com.Biblioteca.Repository.Computo;

import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputoClienteRepository extends JpaRepository<ComputoCliente, Long> {
}
