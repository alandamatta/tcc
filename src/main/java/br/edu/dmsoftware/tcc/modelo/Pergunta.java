package br.edu.dmsoftware.tcc.modelo;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Pergunta implements Serializable{
	
	private Long id;
	private String pergunta;
	private boolean obrigatoria;
	private TipoDeDado tipoDeDado;
	private Anuncio anuncio;
	private boolean ativa;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	
	public boolean isObrigatoria() {
		return obrigatoria;
	}
	public void setObrigatoria(boolean obrigatoria) {
		this.obrigatoria = obrigatoria;
	}
	
	@Enumerated(EnumType.STRING)
	public TipoDeDado getTipoDeDado() {
		return tipoDeDado;
	}
	public void setTipoDeDado(TipoDeDado tipoDeDado) {
		this.tipoDeDado = tipoDeDado;
	}
	
	@ManyToOne
	public Anuncio getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	
	public boolean isAtiva() {
		return ativa;
	}
	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}
	
}
