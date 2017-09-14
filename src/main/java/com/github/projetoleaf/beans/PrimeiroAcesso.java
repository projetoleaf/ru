package com.github.projetoleaf.beans;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "dataNascimento", "tipoRefeicao", "categoria" })
@ToString(of = { "dataNascimento", "tipoRefeicao", "categoria" })
public class PrimeiroAcesso {
	
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;
	
	private TipoRefeicao tipoRefeicao;
	
	private Categoria categoria;
}