package br.edu.dmsoftware.tcc.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Anuncio {

	private Long id;
	private Anunciante anunciante;
	private CategoriaNv1 categoria;
	private Integer reputacao;
	private List<Imagem> imagens;
	private List<Comentario> comentarios;
	private Situacao situacao;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	public Anunciante getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(Anunciante anunciante) {
		this.anunciante = anunciante;
	}

	@ManyToOne
	public CategoriaNv1 getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaNv1 categoria) {
		this.categoria = categoria;
	}
	
	@Column(nullable = false)
	public Integer getReputacao() {
		return reputacao;
	}
	
	public void setReputacao(Integer reputacao) {
		this.reputacao = reputacao;
	}

	@OneToMany(mappedBy = "anuncio")
	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}
	
	@OneToMany(mappedBy = "anuncio")
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	@Enumerated(EnumType.STRING)
	public Situacao getSituacao() {
		return situacao;
	}
	
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
}
