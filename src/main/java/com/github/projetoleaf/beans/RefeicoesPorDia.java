package com.github.projetoleaf.beans;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(of = { "data", "qtdeTradicional", "qtdeVegetariano", "qtdeVegano" })
@ToString(of = { "data", "qtdeTradicional", "qtdeVegetariano", "qtdeVegano" })
public class RefeicoesPorDia {

	private String data;

	private Integer qtdeTradicional;

	private Integer qtdeVegetariano;
	
	private Integer qtdeVegano;
}