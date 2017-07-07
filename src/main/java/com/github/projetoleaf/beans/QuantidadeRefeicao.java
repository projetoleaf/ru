package com.github.projetoleaf.beans;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quantidade_refeicao")
public class QuantidadeRefeicao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Integer subsidiada;
	
	@Column
	private Integer	custo;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSubsidiada() {
		return subsidiada;
	}
	
	public void setSubsidiada(Integer subsidiada) {
		this.subsidiada = subsidiada;
	}
	
	public Integer getCusto() {
		return custo;
	}
	
	public void setCusto(Integer custo) {
		this.custo = custo;
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
		final QuantidadeRefeicao other = (QuantidadeRefeicao) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
	
}
