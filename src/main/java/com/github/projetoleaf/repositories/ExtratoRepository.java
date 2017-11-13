package com.github.projetoleaf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Extrato;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {
	
	List<Extrato> findByCliente(Cliente cliente);
	
	Extrato findFirstByClienteOrderByIdDesc(Cliente cliente);
}
