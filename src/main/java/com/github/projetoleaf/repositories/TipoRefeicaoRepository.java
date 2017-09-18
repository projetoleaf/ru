package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.TipoRefeicao;

@Repository
public interface TipoRefeicaoRepository extends JpaRepository<TipoRefeicao, Long> {

	TipoRefeicao findByDescricao(String descricao);	
}