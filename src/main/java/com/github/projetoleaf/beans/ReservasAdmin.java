package com.github.projetoleaf.beans;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "nome", "creditos", "segundaStatus", "tercaStatus", "quartaStatus", "quintaStatus", "sextaStatus", "valor", "datas" })
@ToString(of = { "nome", "creditos", "segundaStatus", "tercaStatus", "quartaStatus", "quintaStatus", "sextaStatus", "valor", "datas" })
public class ReservasAdmin {
	
	private Long id;
	
	private String nome;
	
	private BigDecimal creditos;
	
	private String segundaStatus;
	
	private String tercaStatus;
	
	private String quartaStatus;
	
	private String quintaStatus;
	
	private String sextaStatus;
	
	private BigDecimal valor;
	
	private List<String> datas;
}