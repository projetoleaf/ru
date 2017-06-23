package com.github.projetoleaf.data;

public class Categoria {

	private int id;
	private String descricao;
	private double valor_sem_subsidio;
	private double valor_com_subsidio;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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

}
