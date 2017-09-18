package com.github.projetoleaf.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "descricao" })
@ToString(of = { "descricao" })
@Entity
@Table(name = "periodo_refeicao")
public class PeriodoRefeicao implements Serializable {

	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "periodo_refeicao_id_seq", sequenceName = "periodo_refeicao_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "periodo_refeicao_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotBlank
	@Column(name = "descricao")
	private String descricao;
}