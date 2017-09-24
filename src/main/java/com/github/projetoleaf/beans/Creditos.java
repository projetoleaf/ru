package com.github.projetoleaf.beans;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "nome", "saldo", "recarga" })
@ToString(of = { "nome", "saldo", "recarga" })
public class Creditos {
	
	private Long id;
	
	private String nome;
	
	private BigDecimal saldo;
	
	private BigDecimal recarga;
}