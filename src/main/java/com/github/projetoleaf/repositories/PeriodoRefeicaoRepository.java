package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.PeriodoRefeicao;

@Repository
public interface PeriodoRefeicaoRepository extends JpaRepository<PeriodoRefeicao, Long> {

}
