package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.Feriado;

@Repository
public interface FeriadoRepository extends JpaRepository<Feriado, Integer> {

}
