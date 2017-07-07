package com.github.projetoleaf.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String cpf;

	@Column
	private String email;

	@Column
	private String senha;

	@Column
	private String nome;

	@Column
	private Integer matricula;

	@OneToOne
	@JoinColumn(name = "id")
	private Curso curso;

	@OneToOne
	@JoinColumn(name = "id")
	private Tipo tipo;	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column	
	private Date data_nascimento;

	@Column
	private String excluido;

	public String getExcluido() {
		return excluido;
	}

	public void setExcluido(String excluido) {
		this.excluido = excluido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
		
	public Integer getCurso() {
		return curso.getId();
	}

	public void setCurso(Integer id) {
		this.curso.setId(id);
	}

	public Integer getTipo() {
		return tipo.getId();
	}

	public void setTipo(Integer id) {
		this.tipo.setId(id);
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 53 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Usuario other = (Usuario) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

}
