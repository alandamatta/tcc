package br.edu.dmsoftware.tcc.modelo;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Anunciante {
	
	private Long id;
	private Usuario usuario;
	private String nomeAnunciante;
	private Calendar dataCadastro;
	private Integer reputacao;
	private List<Anuncio> anuncios;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	//aumentar tamanho
	@Column(length = 10, nullable = false)
	public String getNomeAnunciante() {
		return nomeAnunciante;
	}

	public void setNomeAnunciante(String nomeAnunciante) {
		this.nomeAnunciante = nomeAnunciante;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Column(nullable = false)
	public Integer getReputacao() {
		return reputacao;
	}

	public void setReputacao(Integer reputacao) {
		this.reputacao = reputacao;
	}
	
	@OneToMany(mappedBy = "anunciante")
	public List<Anuncio> getAnuncios() {
		return anuncios;
	}
	
	public void setAnuncios(List<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}

}
