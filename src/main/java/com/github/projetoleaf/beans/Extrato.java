package com.github.projetoleaf.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "cliente", "transacao", "dataTransacao", "saldo" })
@ToString(of = { "cliente", "transacao", "dataTransacao", "saldo" })
@Entity
@Table(name = "extrato")
public class Extrato implements Serializable {

	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "extrato_id_seq", sequenceName = "extrato_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "extrato_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id")
	private Cliente cliente;

	@NotNull
	@NumberFormat(pattern = "#,##0.00")
	@Column(name = "transacao")
	private BigDecimal transacao;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_transacao")
	private Date dataTransacao;

	@NotNull
	@NumberFormat(pattern = "#,##0.00")
	@Column(name = "saldo")
	private BigDecimal saldo;
}