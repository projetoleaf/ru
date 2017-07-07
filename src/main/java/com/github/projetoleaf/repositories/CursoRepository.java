package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

}
