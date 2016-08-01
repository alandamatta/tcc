package br.edu.dmsoftware.tcc.modelo;

import java.beans.Transient;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import br.edu.dmsoftware.tcc.annotation.UniqueEmail;
import br.edu.dmsoftware.tcc.annotation.UniqueUser;

@Entity
public class Usuario {

	private Long id;
	private String email;
	private String confirmaEmail;
	private String usuario;
	private String senha;
	// Transiente
	private String confirmaSenha;
	private String nomeCompleto;
	private String endereco;
	private Integer numEndereco;
	private Cidade cidade;
	private String telefone;
	private String celular;
	private Calendar dataCadastro;
	private Nivel nivel;
	private Situacao situacao;
	private String codVerificacao;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length=80, nullable =false)
	@NotBlank
	@NotNull
	@Email
	@UniqueEmail
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Transient
	public String getConfirmaEmail() {
		return confirmaEmail;
	}
	public void setConfirmaEmail(String confirmaEmail) {
		this.confirmaEmail = confirmaEmail;
	}

	@Column(length = 15, nullable = false)
	@NotBlank
	@NotNull
	@Length(min=4, max=15)
	@UniqueUser
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(length = 32, nullable = false)
	@NotNull
	@NotBlank
	@Length(min=6, max=32)
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Transient
	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	@Column(length = 40, nullable = false)
	@NotNull
	@NotBlank
	@Length(min=8, max=40)
	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	@Column(length = 50, nullable = false)
	@NotNull
	@NotBlank
	@Length(min=10, max=50)
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public Integer getNumEndereco() {
		return numEndereco;
	}
	public void setNumEndereco(Integer numEndereco) {
		this.numEndereco = numEndereco;
	}
	
	@ManyToOne
	@NotNull
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@Column(length = 10, nullable = false)
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(length = 10)
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public Nivel getNivel() {
		return nivel;
	}
	
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public Situacao getSituacao() {
		return situacao;
	}
	
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	@Transient
	public String getCodVerificacao() {
		return codVerificacao;
	}
	
	public void setCodVerificacao(String codVerificacao) {
		this.codVerificacao = codVerificacao;
	}	
}
