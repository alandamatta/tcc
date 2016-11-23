package br.edu.dmsoftware.tcc.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Favorito implements Serializable {

	private Long id;
	private Usuario usuario;
	private Anuncio anuncioFavoritado;
	private Calendar dataAdicionado;
	
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
	
	@ManyToOne
	public Anuncio getAnuncioFavoritado() {
		return anuncioFavoritado;
	}
	public void setAnuncioFavoritado(Anuncio anuncioFavoritado) {
		this.anuncioFavoritado = anuncioFavoritado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataAdicionado() {
		return dataAdicionado;
	}
	public void setDataAdicionado(Calendar dataAdicionado) {
		this.dataAdicionado = dataAdicionado;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((Favorito)obj).id == this.id;
	}
}
