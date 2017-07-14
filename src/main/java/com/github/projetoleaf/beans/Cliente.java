package com.github.projetoleaf.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "cpf", "email", "senha", "nome", "matricula", "curso", "tipo", "dataNascimento" })
@ToString(of = { "cpf", "email", "senha", "nome", "matricula", "curso", "tipo", "dataNascimento" })
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
	@Column(name = "cpf")
	private String cpf;

	@NotBlank
	@Column(name = "email")
	private String email;

	@NotBlank
	@Column(name = "senha")
	private String senha;

	@NotBlank
	@Column(name = "nome")
	private String nome;

	@NotNull
	@Column(name = "matricula")
	private Integer matricula;

	@NotNull
	@ManyToOne
	@Enumerated(EnumType.STRING)
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
	private Curso curso;

	@NotNull
	@OneToOne
	@Enumerated(EnumType.STRING)
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
	private Tipo tipo;	
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento")	
	private Date dataNascimento;
	
	public String convertaStringParaMd5(String valor) {
        MessageDigest mDigest;
        try { 
               //Instanciamos o nosso HASH MD5, poderíamos usar outro como
               //SHA, por exemplo, mas optamos por MD5.
              mDigest = MessageDigest.getInstance("MD5");
                     
              //Convert a String valor para um array de bytes em MD5
              byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));
              
              //Convertemos os bytes para hexadecimal, assim podemos salvar
              //no banco para posterior comparação se senhas
              StringBuffer sb = new StringBuffer();
              for (byte b : valorMD5){
                     sb.append(Integer.toHexString((b & 0xFF) |
                     0x100).substring(1,3));
              }
              
              this.setSenha(sb.toString());
  
              return sb.toString();
                     
        } catch (NoSuchAlgorithmException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
              return null;
        } catch (UnsupportedEncodingException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
              return null;
        }
	}
}