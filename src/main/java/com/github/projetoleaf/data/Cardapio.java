package com.github.projetoleaf.data;

import java.sql.Date;

public class Cardapio {

	private int id;
	private Date data;
	private String prato_base;
	private String prato_tradicional;
	private String prato_vegetariano;
	private String guarnicao;
	private String salada;
	private String sobremesa;
	private String suco;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	
}
