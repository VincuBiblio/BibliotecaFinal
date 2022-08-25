package com.Biblioteca.Repository.Evento;

import com.Biblioteca.Models.Evento.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
