package com.Biblioteca.Repository.CursoTaller;

import com.Biblioteca.Models.CursoTaller.CursoTaller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CursoTallerRepository extends JpaRepository<CursoTaller, Long> {

    Optional<CursoTaller> findByNombre(String nombre);
    Optional<CursoTaller> findByNombreAndFechaInicio(String nombre, Date fechaInicio);
    Boolean existsByNombre(String nombre);

    @Query("SELECT DISTINCT cc.actividades, cc.id FROM Curso cc JOIN cc.cursoTaller ct on cc.cursoTaller.id=ct.id where ct.fechaInicio IN (:fechaInicio)")
    List<CursoTaller> findByFechaInicio(@Param("fechaInicio") Date fechaInicio);}