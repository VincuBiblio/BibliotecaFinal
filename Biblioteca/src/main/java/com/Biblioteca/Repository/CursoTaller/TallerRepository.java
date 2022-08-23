package com.Biblioteca.Repository.CursoTaller;

import com.Biblioteca.DTO.CursoTaller.TallerFecha;
import com.Biblioteca.Models.CursoTaller.CursoTaller;
import com.Biblioteca.Models.CursoTaller.Taller.Taller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TallerRepository extends JpaRepository<Taller,Long> {
    Optional<Taller> findByCursoTaller (CursoTaller cursoTaller);

    @Query(value = "SELECT t.id, ct.nombre, ct.descripcion, ct.observaciones,ct.lugar\n" +
            "FROM taller t, curso_taller ct \n" +
            "where ct.fecha_inicio IN (:fechaInicio) and ct.id = t.curso_taller_id", nativeQuery = true)
    List<TallerFecha> findDistinctByFechaInicio(Date fechaInicio);


}
