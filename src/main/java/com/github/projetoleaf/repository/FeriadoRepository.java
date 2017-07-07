package com.github.projetoleaf.repository;

import com.github.projetoleaf.data.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeriadoRepository extends JpaRepository<Feriado, Integer> {

}
