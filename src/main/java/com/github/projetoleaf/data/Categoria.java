package com.github.projetoleaf.data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String descricao;
	
	@Column
	private double valor_sem_subsidio;
	
	@Column
	private double valor_com_subsidio;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public double getValor_sem_subsidio() {
		return valor_sem_subsidio;
	}
	
	public void setValor_sem_subsidio(double valor_sem_subsidio) {
		this.valor_sem_subsidio = valor_sem_subsidio;
	}
	
	public double getValor_com_subsidio() {
		return valor_com_subsidio;
	}
	
	public void setValor_com_subsidio(double valor_com_subsidio) {
		this.valor_com_subsidio = valor_com_subsidio;
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
		final Categoria other = (Categoria) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

}
