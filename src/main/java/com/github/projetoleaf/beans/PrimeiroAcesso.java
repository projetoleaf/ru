package com.github.projetoleaf.beans;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "dataNascimento", "tipoRefeicao", "categoria" })
@ToString(of = { "dataNascimento", "tipoRefeicao", "categoria" })
public class PrimeiroAcesso {
	
	private Long id;
	
	private Date dataNascimento;
	
	private TipoRefeicao tipoRefeicao;
	
	private Categoria categoria;
}