package com.github.projetoleaf.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "idStatus", "idCliente", "idCardapio" })
@ToString(of = { "idStatus", "idCliente", "idCardapio" })
@Entity
@Table(name = "reserva")
public class Reserva implements Serializable {

	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "reserva_id_seq", sequenceName = "reserva_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "id_status")
	private Integer idStatus;
	
	@NotNull
	@Column(name = "id_cliente")
	private Integer idCliente;
	
	@NotNull
	@Column(name = "id_cardapio")
	private Integer idCardapio;
}