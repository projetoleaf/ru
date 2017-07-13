package com.github.projetoleaf.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "cpf", "email", "senha", "nome", "matricula", "curso", "tipo", "dataNascimento" })
@ToString(of = { "cpf", "email", "senha", "nome", "matricula", "curso", "tipo", "dataNascimento" })
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@NotBlank
	@Column(name = "cpf")
	private String cpf;

	@NotBlank
	@Column(name = "email")
	private String email;

	@NotBlank
	@Column(name = "senha")
	private String senha;

	@NotBlank
	@Column(name = "nome")
	private String nome;

	@NotNull
	@Column(name = "matricula")
	private Integer matricula;

	@NotNull
	@ManyToOne
	@Enumerated(EnumType.STRING)
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
	private Curso curso;

	@NotNull
	@OneToOne
	@Enumerated(EnumType.STRING)
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
	private Tipo tipo;	
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento")	
	private Date dataNascimento;
}