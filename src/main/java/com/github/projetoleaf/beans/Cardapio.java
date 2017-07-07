package com.github.projetoleaf.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "cardapio")
public class Cardapio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column
	private Date data;
	
	@Column
	private String prato_base;
	
	@Column
	private String prato_tradicional;
	
	@Column
	private String prato_vegetariano;
	
	@Column
	private String guarnicao;
	
	@Column
	private String salada;
	
	@Column
	private String sobremesa;
	
	@Column
	private String suco;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getPrato_base() {
		return prato_base;
	}
	
	public void setPrato_base(String prato_base) {
		this.prato_base = prato_base;
	}
	
	public String getPrato_tradicional() {
		return prato_tradicional;
	}
	
	public void setPrato_tradicional(String prato_tradicional) {
		this.prato_tradicional = prato_tradicional;
	}
	
	public String getPrato_vegetariano() {
		return prato_vegetariano;
	}
	
	public void setPrato_vegetariano(String prato_vegetariano) {
		this.prato_vegetariano = prato_vegetariano;
	}
	
	public String getGuarnicao() {
		return guarnicao;
	}
	
	public void setGuarnicao(String guarnicao) {
		this.guarnicao = guarnicao;
	}
	
	public String getSalada() {
		return salada;
	}
	
	public void setSalada(String salada) {
		this.salada = salada;
	}
	
	public String getSobremesa() {
		return sobremesa;
	}
	
	public void setSobremesa(String sobremesa) {
		this.sobremesa = sobremesa;
	}
	
	public String getSuco() {
		return suco;
	}
	
	public void setSuco(String suco) {
		this.suco = suco;
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
		final Cardapio other = (Cardapio) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
	
}
