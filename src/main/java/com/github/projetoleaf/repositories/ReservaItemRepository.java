package com.github.projetoleaf.repositories;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.github.projetoleaf.beans.ReservaItem;

@Repository
public interface ReservaItemRepository extends JpaRepository<ReservaItem, Long> {

	@Query("SELECT COUNT(cardapio.data) FROM ReservaItem r WHERE r.cardapio.data = ?1")
	Integer qtdeDeReservasPorData(Date data);
	
	@Query("SELECT r.id FROM ReservaItem r WHERE r.reserva.cliente.id = ?1 AND r.cardapio.data = ?2")
	Long verificarSeReservaExiste(Long idCliente, Date data);

	@Query("SELECT DISTINCT r.reserva, r.extrato FROM ReservaItem r WHERE r.reserva.cliente.id = ?1")
	List<Object[]> todasAsReservasDoCliente(Long idCliente);
}