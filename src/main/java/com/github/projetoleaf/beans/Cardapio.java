package com.github.projetoleaf.beans;

import java.io.Serializable;
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

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "data", "pratoBase", "pratoTradicional", "pratoVegetariano", "guarnicao", "salada",
		"sobremesa", "suco", "periodoRefeicao" })
@ToString(of = { "data", "pratoBase", "pratoTradicional", "pratoVegetariano", "guarnicao", "salada", "sobremesa",
		"suco", "periodoRefeicao" })
@Entity
@Table(name = "cardapio")
public class Cardapio implements Serializable {

	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "cardapio_id_seq", sequenceName = "cardapio_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cardapio_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data")
	private Date data;

	@NotBlank
	@Column(name = "prato_base")
	private String pratoBase;

	@NotBlank
	@Column(name = "prato_tradicional")
	private String pratoTradicional;

	@NotBlank
	@Column(name = "prato_vegetariano")
	private String pratoVegetariano;

	@NotBlank
	@Column(name = "guarnicao")
	private String guarnicao;

	@NotBlank
	@Column(name = "salada")
	private String salada;

	@NotBlank
	@Column(name = "sobremesa")
	private String sobremesa;

	@NotBlank
	@Column(name = "suco")
	private String suco;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_periodo_refeicao", referencedColumnName = "id")
	private PeriodoRefeicao periodoRefeicao;
}