package br.edu.dmsoftware.tcc.modelo;

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

@Entity
public class Requisicao {
	
	private Long id;
	private Contratante contratante;
	private Anuncio anuncio;
	private Status status;
	private String mensagem; //descrição 
	private Calendar data;
	private Urgencia urgencia;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	public Contratante getContratante() {
		return contratante;
	}
	public void setContratante(Contratante contratante) {
		this.contratante = contratante;
	}
	
	@ManyToOne
	public Anuncio getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	
	@Enumerated(EnumType.STRING)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(nullable = true)
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	@Temporal(TemporalType.DATE)
	public Calendar getData() {
		return data;
	}
	
	public void setData(Calendar data) {
		this.data = data;
	}
	
	@Enumerated(EnumType.STRING)	
	public Urgencia getUrgencia() {
		return urgencia;
	}
	
	public void setUrgencia(Urgencia urgencia) {
		this.urgencia = urgencia;
	}
}
