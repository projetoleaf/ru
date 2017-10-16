package com.github.projetoleaf.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "reserva", "cardapio", "status", "tipoRefeicao", "extrato" })
@ToString(of = { "reserva", "cardapio", "status", "tipoRefeicao", "extrato" })
@Entity
@Table(name = "reserva_item")
public class ReservaItem implements Serializable {

	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "reserva_item_id_seq", sequenceName = "reserva_item_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_item_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_reserva", referencedColumnName = "id")
	private Reserva reserva;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_cardapio", referencedColumnName = "id")
	private Cardapio cardapio;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_status", referencedColumnName = "id")
	private Status status;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_tipo_refeicao", referencedColumnName = "id")
	private TipoRefeicao tipoRefeicao;

	@ManyToOne
	@JoinColumn(name = "id_extrato", referencedColumnName = "id")
	private Extrato extrato;
}