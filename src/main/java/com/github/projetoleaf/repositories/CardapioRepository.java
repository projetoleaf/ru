package com.github.projetoleaf.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.Cardapio;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

	Cardapio findByData(Date data);
	
	@Query("select c.id from Cardapio c where c.data = ?1 and c.periodoRefeicao.id = ?2")
	Long verificarDataEPeriodoRefeicao(Date data, Long idPeriodoRefeicao);
	
	@Query("select c.data from Cardapio c where c.data between ?1 AND ?2 AND c.periodoRefeicao.descricao = 'Almoço'")
	List<Date> todasAsDatasDaSemana(Date segunda, Date sexta);
}