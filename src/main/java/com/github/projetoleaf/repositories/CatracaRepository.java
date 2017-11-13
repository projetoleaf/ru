package com.github.projetoleaf.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.Catraca;
import com.github.projetoleaf.beans.Cliente;

@Repository
public interface CatracaRepository extends JpaRepository<Catraca, Long> {

	List<Catraca> findByCliente(Cliente cliente);
	
	@Query("SELECT c FROM Catraca c WHERE date(c.data) = ?1")
	List<Catraca> findByData(Date data);
	
	@Query("SELECT c FROM Catraca c WHERE c.cliente.nome = ?1 AND date(c.data) = ?2")
	Catraca verificarStatusCatraca(String nome, Date data);
}