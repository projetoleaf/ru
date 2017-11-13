package com.github.projetoleaf.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "nome", "valor", "mensagem", "cardapio", "descricaoTipo", "descricaoStatus" })
@ToString(of = { "nome", "valor", "mensagem", "cardapio", "descricaoTipo", "descricaoStatus" })
public class Geral {
	
	private String nome;
	
	private String valor;

	private String mensagem;

	private Cardapio cardapio;
	
	private String descricaoTipo;

	private String descricaoStatus;
}