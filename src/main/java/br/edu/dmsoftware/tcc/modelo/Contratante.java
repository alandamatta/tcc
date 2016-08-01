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
public class Contratante {

	private Long id;
	private Usuario usuario;
	private Integer reputacao;
	private Calendar dataInicio;
	private List<Requisicao> requisicoes;

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

	@Column(nullable = false)
	public Integer getReputacao() {
		return reputacao;
	}

	public void setReputacao(Integer reputacao) {
		this.reputacao = reputacao;
	}
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)	
	public Calendar getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	@OneToMany(mappedBy = "contratante")
	public List<Requisicao> getRequisicoes() {
		return requisicoes;
	}
	
	public void setRequisicoes(List<Requisicao> requisicoes) {
		this.requisicoes = requisicoes;
	}
}
