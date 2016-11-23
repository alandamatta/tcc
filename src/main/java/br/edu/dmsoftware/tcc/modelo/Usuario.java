package br.edu.dmsoftware.tcc.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;



@Entity
public class Usuario implements Serializable{

	private Long id;
	private Pessoa pessoa;
	private String email;
	private String confirmaEmail;
	private String usuario;
	private String senha;
	private String confirmaSenha;
	private Calendar dataCadastro;
	private Nivel nivel;
	private Situacao situacao;
	private String codVerificacao;
	private Calendar dataUltimoCodVerificacao;
	private boolean emailConfirmado;
	private Double reputacaoContratante;
	private Double reputacaoAnunciante;
	private Calendar dataPrimeiroAnuncio;
	private List<Favorito> favoritos;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne(mappedBy="usuario")
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	@Column(length=80, nullable =false)
	@NotBlank(message="{usuario.email.notBlank}")
	@NotNull(message="{usuario.email.notNull}")
	@Email(message="{usuario.email.email}")
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
	@NotBlank(message="{usuario.usuario.notBlank}")
	@NotNull(message="{usuario.usuario.notNull}")
	@Length(min=4, max=15, message="{usuario.usuario.length}")
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(length = 32, nullable = false)
	@NotNull(message="{usuario.senha.notNull}")
	@NotBlank(message="{usuario.senha.notBlank}")
	@Length(min=6, max=32, message="{usuario.senha.length}")
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
	
	public String getCodVerificacao() {
		return codVerificacao;
	}
	public void setCodVerificacao(String codVerificacao) {
		this.codVerificacao = codVerificacao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataUltimoCodVerificacao() {
		return dataUltimoCodVerificacao;
	}
	public void setDataUltimoCodVerificacao(Calendar dataUltimoCodVerificacao) {
		this.dataUltimoCodVerificacao = dataUltimoCodVerificacao;
	}
	
	public boolean isEmailConfirmado() {
		return emailConfirmado;
	}
	public void setEmailConfirmado(boolean emailConfirmado) {
		this.emailConfirmado = emailConfirmado;
	}	
	
	public Double getReputacaoContratante() {
		return reputacaoContratante;
	}
	public void setReputacaoContratante(Double reputacaoContratante) {
		this.reputacaoContratante = reputacaoContratante;
	}
	
	public Double getReputacaoAnunciante() {
		return reputacaoAnunciante;
	}
	public void setReputacaoAnunciante(Double reputacaoAnunciante) {
		this.reputacaoAnunciante = reputacaoAnunciante;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataPrimeiroAnuncio() {
		return dataPrimeiroAnuncio;
	}
	public void setDataPrimeiroAnuncio(Calendar dataPrimeiroAnuncio) {
		this.dataPrimeiroAnuncio = dataPrimeiroAnuncio;
	}
	
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	public List<Favorito> getFavoritos() {
		return favoritos;
	}
	public void setFavoritos(List<Favorito> favoritos) {
		this.favoritos = favoritos;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return ((Usuario)obj).getId() == this.getId();
	}
	
}
