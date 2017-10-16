package com.github.projetoleaf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

	@Query("SELECT r FROM Reserva r WHERE r.cliente = ?1 ORDER BY id ASC")
	List<Reserva> todasAsReservasDoCliente(Cliente cliente);
}
