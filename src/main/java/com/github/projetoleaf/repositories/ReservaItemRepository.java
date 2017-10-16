package com.github.projetoleaf.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.projetoleaf.beans.ReservaItem;

@Repository
public interface ReservaItemRepository extends JpaRepository<ReservaItem, Long> {

	@Query("SELECT COUNT(cardapio.data) FROM ReservaItem r WHERE r.cardapio.data = ?1 AND r.reserva.tipoValor.descricao = 'Subsidiada'")
	Integer qtdeDeReservasPorData(Date data);

	@Query("SELECT COUNT(cardapio.data) FROM ReservaItem r WHERE r.cardapio.data = ?1 AND r.status.descricao = 'Expirado'")
	Integer qtdeDeReservasExpiradasPorData(Date data);

	@Query("SELECT r.id FROM ReservaItem r WHERE r.reserva.cliente.id = ?1 AND r.cardapio.data = ?2")
	Long verificarSeReservaExiste(Long idCliente, Date data);

	@Query("SELECT r FROM ReservaItem r WHERE r.reserva.cliente.id = ?1")
	List<ReservaItem> todasAsReservasDoCliente(Long idCliente);

	@Query("select r from ReservaItem r where r.cardapio.data between ?1 and ?2")
	List<ReservaItem> todasAsReservasDaSemana(Date segunda, Date sexta);

	@Query("SELECT COUNT(cardapio.data) FROM ReservaItem r WHERE r.cardapio.data = ?1 AND r.reserva.tipoValor.descricao != 'Subsidiada'")
	Integer qtdeDeReservasNãoSubsidiadasPorData(Date data);
}