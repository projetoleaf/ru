package com.github.projetoleaf.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "status", "usuario" })
@ToString(of = { "status", "usuario" })
@Entity
@Table(name = "reserva")
public class Reserva implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@NotNull
	@OneToOne
	@Enumerated(EnumType.STRING)
	@JoinColumn(name = "id_status", referencedColumnName = "id")
	private Status status;
	
	@NotNull
	@OneToOne
	@Enumerated(EnumType.STRING)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	private Usuario usuario;
}