package com.github.projetoleaf.repositories;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.Cardapio;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

	@Query("select c.id, c.data from Cardapio c where c.data = ?1")
	List<Object[]> verificarSeDataExisteNoBD(Date data);
	
	@Query("select c.data from Cardapio c where c.data between ?1 and ?2")
	List<Date> todasAsDatasDaProximaSemana(Date segunda, Date sexta);
}