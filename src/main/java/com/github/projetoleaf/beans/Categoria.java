package com.github.projetoleaf.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "descricao", "valorSemSubsidio", "valorComSubsidio" })
@ToString(of = { "descricao", "valorSemSubsidio", "valorComSubsidio" })
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "categoria_id_seq", sequenceName = "categoria_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	@NotBlank
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@NumberFormat(pattern = "#,##0.00")
	@Column(name = "valor_sem_subsidio")
	private BigDecimal valorSemSubsidio;

	@NotNull
	@NumberFormat(pattern = "#,##0.00")
	@Column(name = "valor_com_subsidio")
	private BigDecimal valorComSubsidio;
}