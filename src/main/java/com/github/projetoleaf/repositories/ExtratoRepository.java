package com.github.projetoleaf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.Extrato;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {

	@Query("SELECT e FROM Extrato e WHERE e.cliente.id = ?1 ORDER BY e.id ASC")
	List<Extrato> buscarTodasTransacoesDoCliente(Long idCliente);
}
