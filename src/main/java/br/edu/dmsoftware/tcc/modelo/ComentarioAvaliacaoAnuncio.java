package br.edu.dmsoftware.tcc.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ComentarioAvaliacaoAnuncio implements Serializable{
	
	private Long id;
	private String comentario;
	private AvaliacaoAnuncio avaliacaoAnuncio;
	private Calendar dataAvaliacao;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@NotBlank
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	@OneToOne
	public AvaliacaoAnuncio getAvaliacaoAnuncio() {
		return avaliacaoAnuncio;
	}
	public void setAvaliacaoAnuncio(AvaliacaoAnuncio avaliacaoAnuncio) {
		this.avaliacaoAnuncio = avaliacaoAnuncio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataAvaliacao() {
		return dataAvaliacao;
	}
	public void setDataAvaliacao(Calendar dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}
	
}
