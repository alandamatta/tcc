package br.edu.dmsoftware.tcc.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class AvaliacaoAnuncio implements Serializable{
	
	private Long id;
	private Integer numeroDeEstrelas;
	private Recomenda recomenda;
	private Servico servico;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getNumeroDeEstrelas() {
		return numeroDeEstrelas;
	}
	public void setNumeroDeEstrelas(Integer numeroDeEstrelas) {
		this.numeroDeEstrelas = numeroDeEstrelas;
	}
	
	@Enumerated(EnumType.STRING)
	@NotNull(message="Recomendação: É obrigatório")
	public Recomenda getRecomenda() {
		return recomenda;
	}
	public void setRecomenda(Recomenda recomenda) {
		this.recomenda = recomenda;
	}
	
	@ManyToOne
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	
	
}
