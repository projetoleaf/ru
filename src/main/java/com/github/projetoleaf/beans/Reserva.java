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
@EqualsAndHashCode(of = { "cliente", "tipoValor", "dataHora" })
@ToString(of = { "cliente", "tipoValor", "dataHora" })
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
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "id_tipo_valor", referencedColumnName = "id")
    private TipoValor tipoValor;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora")	
	private Date dataHora;
}