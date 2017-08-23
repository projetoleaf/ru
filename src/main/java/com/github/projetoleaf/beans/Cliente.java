package com.github.projetoleaf.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "identificacao", "cpf", "nome", "dataNascimento", "dataCriado", "creditos" })
@ToString(of = { "identificacao", "cpf", "nome", "dataNascimento", "dataCriado", "creditos" })
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "cliente_id_seq", sequenceName = "cliente_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotBlank
	@Column(name = "identificacao")
	private String identificacao;

	@NotBlank
	@Column(name = "cpf")
	private String cpf;
	
	@NotBlank
	@Column(name = "nome")
	private String nome;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento")	
	private Date dataNascimento;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criado")	
	private Date dataCriado;
	
	@NotNull
	@NumberFormat(pattern = "#,##0.00")
	@Column(name = "creditos")
	private BigDecimal creditos;
}