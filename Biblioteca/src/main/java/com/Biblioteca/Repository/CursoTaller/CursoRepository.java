package com.Biblioteca.Repository.CursoTaller;

import com.Biblioteca.DTO.CursoTaller.CursoFecha;
import com.Biblioteca.Models.CursoTaller.Curso.Curso;
import com.Biblioteca.Models.CursoTaller.CursoTaller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso,Long> {

    Optional<Curso> findByCursoTaller (CursoTaller cursoTaller);

    @Query(value = "SELECT c.id,c.actividades, c.materiales,c.num_participantes, ct.nombre, ct.descripcion, ct.observaciones,ct.lugar\n" +
            "FROM curso c, curso_taller ct \n" +
            "where ct.fecha_inicio IN (:fechaInicio) and ct.id = c.curso_taller_id", nativeQuery = true)
    List<CursoFecha> findByFechaInicio(Date fechaInicio);


}



