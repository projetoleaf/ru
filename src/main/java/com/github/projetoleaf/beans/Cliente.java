package com.github.projetoleaf.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "identificacao", "cpf", "nome", "dataNascimento", "dataCriado", "creditos" })
@ToString(of = { "identificacao", "cpf", "nome", "dataNascimento", "dataCriado", "creditos" })
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
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento")	
	private Date dataNascimento;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criado")	
	private Date dataCriado;
	
	@NotNull
	@NumberFormat(pattern = "#,##0.00")
	@Column(name = "creditos")
	private BigDecimal creditos;
	
	@NotBlank
	@Column(name = "biometria")
	private String biometria;
	
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<Reserva> reservas;
	
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<ClienteCategoria> clientesCategoria;
	
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<ClienteTipoRefeicao> clientesTipoRefeicao;
	
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<Extrato> extratos;
	
	public String imprimeCPF(String CPF) {
	    return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
	      CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
	}
}