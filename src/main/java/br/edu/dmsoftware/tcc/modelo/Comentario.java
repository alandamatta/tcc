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
public class Comentario {

	private Long id;
	private String mensagem;
	private Usuario usuario;
	private Calendar dataComentario;
	private Anuncio anuncio;
	private Integer util; //comentário útil?
	private Integer Inutil; //ou não
	private List<Resposta> respostas;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	@Column(length = 200, nullable = false)
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	@ManyToOne
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Calendar getDataComentario() {
		return dataComentario;
	}

	public void setDataComentario(Calendar dataComentario) {
		this.dataComentario = dataComentario;
	}
	
	@ManyToOne
	public Anuncio getAnuncio() {
		return anuncio;
	}
	
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	
	@Column(nullable = true)
	public Integer getUtil() {
		return util;
	}
	
	public void setUtil(Integer util) {
		this.util = util;
	}
	
	@Column(nullable = true)
	public Integer getInutil() {
		return Inutil;
	}
	
	public void setInutil(Integer inutil) {
		Inutil = inutil;
	}
	
	@OneToMany(mappedBy = "comentario")
	public List<Resposta> getRespostas() {
		return respostas;
	}
	
	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

}
