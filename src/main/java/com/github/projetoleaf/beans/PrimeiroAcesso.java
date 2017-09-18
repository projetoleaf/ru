package com.github.projetoleaf.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "categoria", "raMatricula", "curso" })
@ToString(of = { "categoria", "raMatricula", "curso" })
public class PrimeiroAcesso {
	
	private Long id;
	
	private Categoria categoria;
	
	private Integer raMatricula;
	
	private Curso curso;
}