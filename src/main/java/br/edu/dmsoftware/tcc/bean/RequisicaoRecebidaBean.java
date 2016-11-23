package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.PerguntaDao;
import br.edu.dmsoftware.tcc.dao.RequisicaoDao;
import br.edu.dmsoftware.tcc.dao.ResPerAnuncioDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Pergunta;
import br.edu.dmsoftware.tcc.modelo.Requisicao;
import br.edu.dmsoftware.tcc.modelo.ResPerAnuncio;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.StatusRequisicao;

@Model
@ViewScoped
public class RequisicaoRecebidaBean implements Serializable{
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private RequisicaoDao requisicaoDao;
	private List<Requisicao> requisicoesNaoLidas;
	private List<Requisicao> requisicoesLidas;
	private List<Requisicao> requisicoesEnviadoOrcamento;
	private List<Requisicao> requisicoesFinalizadoContratado;
	private List<Requisicao> requisicoesFinalizadoRecusadoOrcamento;
	private List<Requisicao> requisicoesFinalizadoRecusado;
	
	@Inject
	private ResPerAnuncioDao resPerAnuncioDao; 
	private List<ResPerAnuncio> resPerAnuncios;
	
	private boolean mostraRepeatNaoLidas = false;
	private boolean mostraRepeatLidas = false;
	private boolean mostraRepeatEnviadoOrcamento = false;
	private boolean mostraRepeatFinalizadoContratado = false;
	private boolean mostraRepeatFinalizadoRecusadoOrcamento = false;
	private boolean mostraRepeatFinalizadoRecusado = false;
	
	private boolean mostraLoaderNaoLidas = false;
	private boolean mostraLoaderLidas = false;
	private boolean mostraLoaderEnviadoOrcamento = false;
	private boolean mostraLoaderFinalizadoContratado = false;
	private boolean mostraLoaderFinalizadoRecusadoOrcamento = false;
	private boolean mostraLoaderFinalizadoRecusado = false;
	
	@PostConstruct
	public void init(){
		carregaRequisicoesLidas();
		carregaRequisicoesNaoLidas();
		carregaRequisicoesEnviadoOrcamento();
		carregaRequisicoesFinalizadoContratado();
		carregaRequisicoesFinalizadoRecusado();
		carregaRequisicoesFinalizadoRecusadoOrcamento();
		
		if((!requisicoesLidas.isEmpty())&&(requisicoesLidas.size() > 7)){
			setMostraLoaderLidas(true);
			setMostraRepeatLidas(true);
		}else if((!requisicoesLidas.isEmpty()) && (requisicoesLidas.size() <= 7)){
			setMostraLoaderLidas(false);
			setMostraRepeatLidas(true);
		}else{
			setMostraRepeatLidas(false);
			setMostraLoaderLidas(false);
		}
		
		if((!requisicoesNaoLidas.isEmpty()) && (requisicoesNaoLidas.size() > 7)){
			setMostraLoaderNaoLidas(true);
			setMostraRepeatNaoLidas(true);
		}else if((!requisicoesNaoLidas.isEmpty()) && (requisicoesNaoLidas.size() <= 7)){
			setMostraLoaderNaoLidas(false);
			setMostraRepeatNaoLidas(true);
		}else{
			setMostraLoaderNaoLidas(false);
			setMostraRepeatNaoLidas(false);
		}
		
		if((!requisicoesEnviadoOrcamento.isEmpty()) && (requisicoesEnviadoOrcamento.size() > 7)){
			setMostraLoaderEnviadoOrcamento(true);
			setMostraRepeatEnviadoOrcamento(true);
		}else if((!requisicoesEnviadoOrcamento.isEmpty()) && (requisicoesEnviadoOrcamento.size() <= 7)){
			setMostraLoaderEnviadoOrcamento(false);
			setMostraRepeatEnviadoOrcamento(true);
		}else{
			setMostraLoaderEnviadoOrcamento(false);
			setMostraRepeatEnviadoOrcamento(false);
		}
		
		if((!requisicoesFinalizadoContratado.isEmpty()) && (requisicoesFinalizadoContratado.size() > 7)){
			setMostraLoaderFinalizadoContratado(true);
			setMostraRepeatFinalizadoContratado(true);
		}else if((!requisicoesFinalizadoContratado.isEmpty()) && (requisicoesFinalizadoContratado.size() <= 7)){
			setMostraLoaderFinalizadoContratado(false);
			setMostraRepeatFinalizadoContratado(true);
		}else{
			setMostraLoaderFinalizadoContratado(false);
			setMostraRepeatFinalizadoContratado(false);
		}
		
		if((!requisicoesFinalizadoRecusadoOrcamento.isEmpty()) && (requisicoesFinalizadoRecusadoOrcamento.size() > 7)){
			setMostraLoaderFinalizadoRecusadoOrcamento(true);
			setMostraRepeatFinalizadoRecusadoOrcamento(true);
		}else if((!requisicoesFinalizadoRecusadoOrcamento.isEmpty()) && (requisicoesFinalizadoRecusadoOrcamento.size() <= 7)){
			setMostraLoaderFinalizadoRecusadoOrcamento(false);
			setMostraRepeatFinalizadoRecusadoOrcamento(true);
		}else{
			setMostraLoaderFinalizadoRecusadoOrcamento(false);
			setMostraRepeatFinalizadoRecusadoOrcamento(false);
		}
		
		if((!requisicoesFinalizadoRecusado.isEmpty()) && (requisicoesFinalizadoRecusado.size() > 7)){
			setMostraLoaderFinalizadoRecusado(true);
			setMostraRepeatFinalizadoRecusado(true);
		}else if((!requisicoesFinalizadoRecusado.isEmpty()) && (requisicoesFinalizadoRecusado.size() <= 7)){
			setMostraLoaderFinalizadoRecusado(false);
			setMostraRepeatFinalizadoRecusado(true);
		}else{
			setMostraLoaderFinalizadoRecusado(false);
			setMostraRepeatFinalizadoRecusado(false);
		}
		
	}
	
	@Transactional
	public void carregaResPerAnuncios(Requisicao requisicao){
		resPerAnuncios = resPerAnuncioDao.porRequisicao(requisicao);
		if(requisicao.getStatus().equals(StatusRequisicao.NAO_LIDA)){
			requisicao.setStatus(StatusRequisicao.LIDA);
			requisicaoDao.salvar(requisicao);
		}
	}
	
	@Transactional
	public void rejeitar(Requisicao requisicao){
		try {
			requisicao.setSituacao(Situacao.INATIVO);
			requisicao.setStatus(StatusRequisicao.FINALIZADO_RECUSADO);
			requisicaoDao.salvar(requisicao);
		} catch (Exception e) {
			new Mensagens().acaoEfetuadaComErro();
		}
		
	}
	
	public void carregaRequisicoesNaoLidas(){
		requisicoesNaoLidas = requisicaoDao.porAnunciantePorStatus(usuarioLogado.getUsuario(), StatusRequisicao.NAO_LIDA, Situacao.ATIVO);
	}
	
	public void carregaRequisicoesLidas(){
		requisicoesLidas = requisicaoDao.porAnunciantePorStatus(usuarioLogado.getUsuario(), StatusRequisicao.LIDA, Situacao.ATIVO);
	}
	
	public void carregaRequisicoesEnviadoOrcamento(){
		requisicoesEnviadoOrcamento = requisicaoDao.porAnunciantePorStatus(usuarioLogado.getUsuario(), StatusRequisicao.ENVIADO_ORCAMENTO, Situacao.INATIVO);
	}
	
	public void carregaRequisicoesFinalizadoContratado(){
		requisicoesFinalizadoContratado = requisicaoDao.porAnunciantePorStatus(usuarioLogado.getUsuario(), StatusRequisicao.FINALIZADO_CONTRATADO, Situacao.INATIVO);
	}
	
	public void carregaRequisicoesFinalizadoRecusadoOrcamento(){
		requisicoesFinalizadoRecusadoOrcamento = requisicaoDao.porAnunciantePorStatus(usuarioLogado.getUsuario(), StatusRequisicao.FINALIZADO_RECUSADO_ORCAMENTO, Situacao.INATIVO);
	}
	
	public void carregaRequisicoesFinalizadoRecusado(){
		requisicoesFinalizadoRecusado = requisicaoDao.porAnunciantePorStatus(usuarioLogado.getUsuario(), StatusRequisicao.FINALIZADO_RECUSADO, Situacao.INATIVO);
	}
	
	//GETTERS AND SETTERS
	

	public List<ResPerAnuncio> getResPerAnuncios() {
		return resPerAnuncios;
	}
	public void setResPerAnuncios(List<ResPerAnuncio> resPerAnuncios) {
		this.resPerAnuncios = resPerAnuncios;
	}

	public List<Requisicao> getRequisicoesNaoLidas() {
		return requisicoesNaoLidas;
	}

	public void setRequisicoesNaoLidas(List<Requisicao> requisicoesNaoLidas) {
		this.requisicoesNaoLidas = requisicoesNaoLidas;
	}

	public List<Requisicao> getRequisicoesLidas() {
		return requisicoesLidas;
	}
	
	public List<Requisicao> getRequisicoesEnviadoOrcamento() {
		return requisicoesEnviadoOrcamento;
	}

	public void setRequisicoesEnviadoOrcamento(List<Requisicao> requisicoesEnviadoOrcamento) {
		this.requisicoesEnviadoOrcamento = requisicoesEnviadoOrcamento;
	}

	public List<Requisicao> getRequisicoesFinalizadoContratado() {
		return requisicoesFinalizadoContratado;
	}

	public void setRequisicoesFinalizadoContratado(List<Requisicao> requisicoesFinalizadoContratado) {
		this.requisicoesFinalizadoContratado = requisicoesFinalizadoContratado;
	}

	public List<Requisicao> getRequisicoesFinalizadoRecusadoOrcamento() {
		return requisicoesFinalizadoRecusadoOrcamento;
	}

	public void setRequisicoesFinalizadoRecusadoOrcamento(List<Requisicao> requisicoesFinalizadoRecusadoOrcamento) {
		this.requisicoesFinalizadoRecusadoOrcamento = requisicoesFinalizadoRecusadoOrcamento;
	}

	public List<Requisicao> getRequisicoesFinalizadoRecusado() {
		return requisicoesFinalizadoRecusado;
	}

	public void setRequisicoesFinalizadoRecusado(List<Requisicao> requisicoesFinalizadoRecusado) {
		this.requisicoesFinalizadoRecusado = requisicoesFinalizadoRecusado;
	}

	public void setRequisicoesLidas(List<Requisicao> requisicoesLidas) {
		this.requisicoesLidas = requisicoesLidas;
	}

	public boolean isMostraRepeatNaoLidas() {
		return mostraRepeatNaoLidas;
	}

	public void setMostraRepeatNaoLidas(boolean mostraRepeatNaoLidas) {
		this.mostraRepeatNaoLidas = mostraRepeatNaoLidas;
	}

	public boolean isMostraRepeatLidas() {
		return mostraRepeatLidas;
	}

	public void setMostraRepeatLidas(boolean mostraRepeatLidas) {
		this.mostraRepeatLidas = mostraRepeatLidas;
	}

	public boolean isMostraRepeatEnviadoOrcamento() {
		return mostraRepeatEnviadoOrcamento;
	}

	public void setMostraRepeatEnviadoOrcamento(boolean mostraRepeatEnviadoOrcamento) {
		this.mostraRepeatEnviadoOrcamento = mostraRepeatEnviadoOrcamento;
	}

	public boolean isMostraRepeatFinalizadoContratado() {
		return mostraRepeatFinalizadoContratado;
	}

	public void setMostraRepeatFinalizadoContratado(boolean mostraRepeatFinalizadoContratado) {
		this.mostraRepeatFinalizadoContratado = mostraRepeatFinalizadoContratado;
	}

	public boolean isMostraRepeatFinalizadoRecusadoOrcamento() {
		return mostraRepeatFinalizadoRecusadoOrcamento;
	}

	public void setMostraRepeatFinalizadoRecusadoOrcamento(boolean mostraRepeatFinalizadoRecusadoOrcamento) {
		this.mostraRepeatFinalizadoRecusadoOrcamento = mostraRepeatFinalizadoRecusadoOrcamento;
	}

	public boolean isMostraRepeatFinalizadoRecusado() {
		return mostraRepeatFinalizadoRecusado;
	}

	public void setMostraRepeatFinalizadoRecusado(boolean mostraRepeatFinalizadoRecusado) {
		this.mostraRepeatFinalizadoRecusado = mostraRepeatFinalizadoRecusado;
	}

	public boolean isMostraLoaderNaoLidas() {
		return mostraLoaderNaoLidas;
	}

	public void setMostraLoaderNaoLidas(boolean mostraLoaderNaoLidas) {
		this.mostraLoaderNaoLidas = mostraLoaderNaoLidas;
	}

	public boolean isMostraLoaderLidas() {
		return mostraLoaderLidas;
	}

	public void setMostraLoaderLidas(boolean mostraLoaderLidas) {
		this.mostraLoaderLidas = mostraLoaderLidas;
	}

	public boolean isMostraLoaderEnviadoOrcamento() {
		return mostraLoaderEnviadoOrcamento;
	}

	public void setMostraLoaderEnviadoOrcamento(boolean mostraLoaderEnviadoOrcamento) {
		this.mostraLoaderEnviadoOrcamento = mostraLoaderEnviadoOrcamento;
	}

	public boolean isMostraLoaderFinalizadoContratado() {
		return mostraLoaderFinalizadoContratado;
	}

	public void setMostraLoaderFinalizadoContratado(boolean mostraLoaderFinalizadoContratado) {
		this.mostraLoaderFinalizadoContratado = mostraLoaderFinalizadoContratado;
	}

	public boolean isMostraLoaderFinalizadoRecusadoOrcamento() {
		return mostraLoaderFinalizadoRecusadoOrcamento;
	}

	public void setMostraLoaderFinalizadoRecusadoOrcamento(boolean mostraLoaderFinalizadoRecusadoOrcamento) {
		this.mostraLoaderFinalizadoRecusadoOrcamento = mostraLoaderFinalizadoRecusadoOrcamento;
	}

	public boolean isMostraLoaderFinalizadoRecusado() {
		return mostraLoaderFinalizadoRecusado;
	}

	public void setMostraLoaderFinalizadoRecusado(boolean mostraLoaderFinalizadoRecusado) {
		this.mostraLoaderFinalizadoRecusado = mostraLoaderFinalizadoRecusado;
	}
	
}
