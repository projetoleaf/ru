package com.github.projetoleaf.beans;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "nome", "creditos", "segundaStatus", "tercaStatus", "quartaStatus", "quintaStatus",
		"sextaStatus", "recargas", "valor", "data", "utilizarCreditos" })
@ToString(of = { "nome", "creditos", "segundaStatus", "tercaStatus", "quartaStatus", "quintaStatus", "sextaStatus",
		"recargas", "valor", "data", "utilizarCreditos" })
public class ReservasAdmin {

	private Long id;

	private String nome;

	private String creditos;

	private String segundaStatus;

	private String tercaStatus;

	private String quartaStatus;

	private String quintaStatus;

	private String sextaStatus;

	private BigDecimal recargas;

	private BigDecimal valor;

	private Date data;

	private boolean utilizarCreditos;
}