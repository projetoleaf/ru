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
@EqualsAndHashCode(of = { "subsidiada", "custo" })
@ToString(of = { "subsidiada", "custo" })
@Entity
@Table(name = "quantidade_refeicao")
public class QuantidadeRefeicao implements Serializable {
	
	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "quantidade_refeicao_id_seq", sequenceName = "quantidade_refeicao_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quantidade_refeicao_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "subsidiada")
	private Integer subsidiada;
	
	@NotNull
	@Column(name = "custo")
	private Integer	custo;
}