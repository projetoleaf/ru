package com.github.projetoleaf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.TipoValor;

@Repository
public interface TipoValorRepository extends JpaRepository<TipoValor, Long> {

	@Query("select t.id from TipoValor t where t.descricao = 'Subsidiada'")
	Long buscarIdDoTipoValorSubsidiada();
}
