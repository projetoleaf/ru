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
@EqualsAndHashCode(of = { "cliente", "tipoRefeicao", "ativo" })
@ToString(of = { "cliente", "tipoRefeicao", "ativo" })
@Entity
@Table(name = "cliente_tipo_refeicao")
public class ClienteTipoRefeicao implements Serializable {

	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "cliente_tipo_refeicao_id_seq", sequenceName = "cliente_tipo_refeicao_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_tipo_refeicao_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "id_tipo_refeicao", referencedColumnName = "id")
    private TipoRefeicao tipoRefeicao;
	
	@NotNull
	@Column(name = "ativo")	
	private Boolean ativo;
}