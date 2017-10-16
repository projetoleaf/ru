package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.QuantidadeRefeicao;

@Repository
public interface QuantidadeRefeicaoRepository extends JpaRepository<QuantidadeRefeicao, Long> {

}
