package com.github.projetoleaf.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "setor", "coluna", "linha", "planilha" })
@ToString(of = { "setor", "coluna", "linha", "planilha" })
public class Relatorios {
	
	private String setor;

	private String coluna;

	private String linha;
	
	private String planilha;
}