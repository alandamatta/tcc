package br.edu.dmsoftware.tcc.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Requisicao implements Serializable{
	
	private Long id;
	private Situacao situacao;
	private Usuario contratante;//get e set com nomes diferentes (usuario)
	private Anuncio anuncio;
	private StatusRequisicao status;
	private String mensagem; //descrição 
	private Calendar data;
	private Urgencia urgencia;
	private Integer cep;
	private String enderecoDoServico;
	private String numero;
	private String logradouro;
	private String bairro;
	private Cidade cidade;
	private Date dataInicio;
	private Date dataFim;
	private String descricaoMotivoRejeicao;
	private boolean notificado;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Enumerated(EnumType.STRING)
	public Situacao getSituacao() {
		return situacao;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	@ManyToOne
	public Usuario getUsuario() {
		return contratante;
	}
	public void setUsuario(Usuario contratante) {
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
	public StatusRequisicao getStatus() {
		return status;
	}
	public void setStatus(StatusRequisicao status) {
		this.status = status;
	}
	
	@NotNull(message="{requisicao.mensagem.notnull}")
	@NotBlank(message="{requisicao.mensagem.notBlank}")
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
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
	
	@Column(nullable=true)
	public Integer getCep() {
		return cep;
	}
	public void setCep(Integer cep) {
		this.cep = cep;
	}
	
	@NotNull(message="{requisicao.enderecoDoServico.notNull}")
	@NotBlank(message="{requisicao.enderecoDoServico.notBlank}")
	public String getEnderecoDoServico() {
		return enderecoDoServico;
	}
	public void setEnderecoDoServico(String enderecoDoServico) {
		this.enderecoDoServico = enderecoDoServico;
	}
	
	@NotNull(message="{requisicao.numero.notNull}")
	@NotBlank(message="{requisicao.numero.notBlank}")
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	@ManyToOne
	@NotNull(message="requisicao.cidade.notNull}")
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@Temporal(TemporalType.DATE)
	@NotNull(message="{requisicao.dataInicio.notNull}")
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	@Temporal(TemporalType.DATE)
	@NotNull(message="{requisicao.dataFim.notNull}")
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public String getDescricaoMotivoRejeicao() {
		return descricaoMotivoRejeicao;
	}
	public void setDescricaoMotivoRejeicao(String descricaoMotivoRejeicao) {
		this.descricaoMotivoRejeicao = descricaoMotivoRejeicao;
	}
	
	public boolean isNotificado() {
		return notificado;
	}
	public void setNotificado(boolean notificado) {
		this.notificado = notificado;
	}
	
}
