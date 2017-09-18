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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "cliente", "categoria", "raMatricula", "dataInicio", "dataFim" })
@ToString(of = { "cliente", "categoria", "raMatricula", "dataInicio", "dataFim" })
@Entity
@Table(name = "cliente_categoria")
public class ClienteCategoria implements Serializable {

	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "cliente_categoria_id_seq", sequenceName = "cliente_categoria_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_categoria_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
	private Cliente cliente;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;
	
	@NotNull
	@Column(name = "ra_matricula")
	private Integer raMatricula;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_inicio")	
	private Date dataInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_fim")	
	private Date dataFim;
}