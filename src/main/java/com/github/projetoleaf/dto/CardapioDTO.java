package com.github.projetoleaf.dto;

import java.util.Date;
import java.util.Objects;

public class CardapioDTO {

	private Integer id;
	
	private Date data;
	
	private String prato_base;
	
	private String prato_tradicional;
	
	private String prato_vegetariano;
	
	private String guarnicao;
	
	private String salada;
	
	private String sobremesa;
	
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
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final CardapioDTO other = (CardapioDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}