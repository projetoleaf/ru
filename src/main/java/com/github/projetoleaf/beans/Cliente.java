package com.github.projetoleaf.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "identificacao", "cpf", "nome", "biometria", "dataCriado" })
@ToString(of = { "identificacao", "cpf", "nome", "biometria", "dataCriado" })
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 44393616612232895L;

	@Id
	@SequenceGenerator(name = "cliente_id_seq", sequenceName = "cliente_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotBlank
	@Column(name = "identificacao")
	private String identificacao;

	@NotBlank
	@Column(name = "cpf")
	private String cpf;
	
	@NotBlank
	@Column(name = "nome")
	private String nome;	
	
	@NotBlank
	@Column(name = "biometria")
	private String biometria;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criado")	
	private Date dataCriado;
	
	public String imprimeCPF(String CPF) {
	    return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
	      CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
	}
}