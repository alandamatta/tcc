package br.edu.dmsoftware.tcc.modelo;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Orcamento implements Serializable{
	
	private Long id;
	private Requisicao requisicao;
	private Double valor;
	private String observacaoValor;
	private Date dataInicio;
	private Date dataFim;
	private String observacaoDatas;
	private String mensagem;
	private Date dataOrcamento;
	private Date dataVencimento;
	private MotivoRejOrcamento motivoRejeitado;
	private String descricaoMotivoDescricao;
	private StatusOrcamento status;
	private Situacao situacao;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	public Requisicao getRequisicao() {
		return requisicao;
	}
	public void setRequisicao(Requisicao requisicao) {
		this.requisicao = requisicao;
	}
	
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public String getObservacaoValor() {
		return observacaoValor;
	}
	public void setObservacaoValor(String observacaoValor) {
		this.observacaoValor = observacaoValor;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public String getObservacaoDatas() {
		return observacaoDatas;
	}
	public void setObservacaoDatas(String observacaoDatas) {
		this.observacaoDatas = observacaoDatas;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	@Lob
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDataOrcamento() {
		return dataOrcamento;
	}
	public void setDataOrcamento(Date dataOrcamento) {
		this.dataOrcamento = dataOrcamento;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	@Enumerated(EnumType.STRING)
	public MotivoRejOrcamento getMotivoRejeitado() {
		return motivoRejeitado;
	}
	public void setMotivoRejeitado(MotivoRejOrcamento motivoRejeitado) {
		this.motivoRejeitado = motivoRejeitado;
	}
	
	public String getDescricaoMotivoDescricao() {
		return descricaoMotivoDescricao;
	}
	public void setDescricaoMotivoDescricao(String descricaoMotivoDescricao) {
		this.descricaoMotivoDescricao = descricaoMotivoDescricao;
	}
	
	public StatusOrcamento getStatus() {
		return status;
	}
	public void setStatus(StatusOrcamento status) {
		this.status = status;
	}
	
	@Enumerated(EnumType.STRING)
	public Situacao getSituacao() {
		return situacao;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
}
