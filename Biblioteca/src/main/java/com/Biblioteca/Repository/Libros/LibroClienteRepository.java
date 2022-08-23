package com.Biblioteca.Repository.Libros;

import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroClienteRepository extends JpaRepository<PrestamoLibroCliente,Long> {
}
