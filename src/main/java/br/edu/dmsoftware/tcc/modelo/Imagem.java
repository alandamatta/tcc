package br.edu.dmsoftware.tcc.modelo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Imagem {

	private Long id;
	private String diretorio;
	private Anuncio anuncio;
	private Usuario usuario;
	private Calendar dataUpload;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	
	@ManyToOne
	public Anuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	@ManyToOne
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(Calendar dataUpload) {
		this.dataUpload = dataUpload;
	}

}
