package br.edu.dmsoftware.tcc.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Servico implements Serializable{
	
	private Long id;
	private Orcamento orcamento;
	private Date dataDeContratoDoServico;
	private Date dataDoServicoFinalizado;
	private Situacao situacao;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne
	public Orcamento getOrcamento() {
		return orcamento;
	}
	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDataDeContratoDoServico() {
		return dataDeContratoDoServico;
	}
	public void setDataDeContratoDoServico(Date dataDeContratoDoServico) {
		this.dataDeContratoDoServico = dataDeContratoDoServico;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDataDoServicoFinalizado() {
		return dataDoServicoFinalizado;
	}
	public void setDataDoServicoFinalizado(Date dataDoServicoFinalizado) {
		this.dataDoServicoFinalizado = dataDoServicoFinalizado;
	}
	
	@Enumerated(EnumType.STRING)
	public Situacao getSituacao() {
		return situacao;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
}
