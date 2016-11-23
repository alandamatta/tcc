package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.sun.mail.imap.protocol.Status;

import br.edu.dmsoftware.tcc.dao.OrcamentoDao;
import br.edu.dmsoftware.tcc.dao.RequisicaoDao;
import br.edu.dmsoftware.tcc.dao.ResPerAnuncioDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.MotivoRejRequisicao;
import br.edu.dmsoftware.tcc.modelo.Orcamento;
import br.edu.dmsoftware.tcc.modelo.Requisicao;
import br.edu.dmsoftware.tcc.modelo.ResPerAnuncio;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.StatusOrcamento;
import br.edu.dmsoftware.tcc.modelo.StatusRequisicao;

@Model
@ViewScoped
public class MontarOrcamentoBean implements Serializable{
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	
	private Long requisicaoId;
	@Inject
	private Requisicao requisicao;
	@Inject
	private RequisicaoDao requisicaoDao;
	@Inject
	private Orcamento orcamento;
	@Inject
	private OrcamentoDao orcamentoDao;
	@Inject
	private ResPerAnuncioDao resPerAnuncioDao;
	private List<ResPerAnuncio> resPerAnuncios;
	private String dataAtual;
	private Long diferencaDeDias;
	private boolean renderizaDados = false;
	private boolean renderizaPaginaInexistente = false;
	private boolean dataFimDisable = true;
	
	@Transactional
	public void preRenderMethod(){
		try {
			carregarRequisicao();
			validaEntradaDoFormulario();
			carregaResPerAnuncio();
			pegarDataAtual();
			calculaPeriodo();
		} catch (Exception e) {
			setRenderizaDados(false);
			setRenderizaPaginaInexistente(true);
		}
	}
	
	@Transactional
	private void validaEntradaDoFormulario(){
		if(this.requisicaoDao.existe(this.requisicao)){
			if(this.requisicao.getAnuncio().getUsuario().equals(usuarioLogado.getUsuario())){//usuário logado é criador do anuncio que recebeu requisicao
				if(this.requisicao.getStatus().equals(StatusRequisicao.NAO_LIDA)){
					this.requisicao.setStatus(StatusRequisicao.LIDA);
					this.requisicaoDao.salvar(requisicao);
				}
				setRenderizaDados(true);
				setRenderizaPaginaInexistente(false);
				alteraStatusRequisicao();
			}else{
				setRenderizaDados(false);
				setRenderizaPaginaInexistente(true);
			}
		}else{
			setRenderizaDados(false);
			setRenderizaPaginaInexistente(true);
		}
	}
	
	@Transactional
	public String enviarOrcamento(){
		try {
			this.requisicao.setStatus(StatusRequisicao.ENVIADO_ORCAMENTO);
			this.requisicao.setSituacao(Situacao.INATIVO);
			this.requisicaoDao.salvar(this.requisicao);
			orcamento.setDataOrcamento(new Date()); //possível erro
			orcamento.setStatus(StatusOrcamento.NAO_LIDA);
			orcamento.setSituacao(Situacao.ATIVO);
			orcamento.setRequisicao(this.requisicao);
			orcamentoDao.salvar(this.orcamento);
			return "/anunciante/requisicaoRecebida.jsf";
		} catch (Exception e) {
			new Mensagens().acaoEfetuadaComErro();
			return "";
		}
	}
	
	public String redirecionaRejeicao(){
		return "/contratante/rejeitaRequisicao.jsf?faces-redirect=true&requisicaoId="+this.requisicaoId;
	}
	
	@Transactional
	private void alteraStatusRequisicao(){
		if(this.requisicao.getStatus().equals(StatusRequisicao.NAO_LIDA)){
			this.requisicao.setStatus(StatusRequisicao.LIDA);
			this.requisicaoDao.salvar(this.requisicao);
		}
	}
	
	private void carregarRequisicao(){
		this.requisicao = requisicaoDao.buscaPeloId(requisicaoId);
	}
	
	private void carregaResPerAnuncio(){
		this.resPerAnuncios = resPerAnuncioDao.porRequisicao(this.requisicao);
	}
	
	private void pegarDataAtual(){
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yy");
		Date data = new Date();
		dataAtual = simpleDate.format(data);
	}
	
	public void dataInicioAjax(){
		if(this.orcamento.getDataInicio() != null){
			setDataFimDisable(false);
		}
	}
	
	private void calculaPeriodo(){
		diferencaDeDias = ((this.requisicao.getDataInicio().getTime() - this.requisicao.getDataFim().getTime()) / (1000*60*60*24)) * -1;
	}
	
	public String redirecionaAnuncio(){
		String T = "/anuncio.jsf?faces-redirect=true";
		System.out.println(T);
		return T;
	}
	
	public Long getRequisicaoId() {
		return requisicaoId;
	}
	public void setRequisicaoId(Long requisicaoId) {
		this.requisicaoId = requisicaoId;
	}
	
	public Requisicao getRequisicao() {
		return requisicao;
	}
	public void setRequisicao(Requisicao requisicao) {
		this.requisicao = requisicao;
	}
	
	public Orcamento getOrcamento() {
		return orcamento;
	}
	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}
	
	public boolean isRenderizaDados() {
		return renderizaDados;
	}
	public void setRenderizaDados(boolean renderizaDados) {
		this.renderizaDados = renderizaDados;
	}
	
	public boolean isRenderizaPaginaInexistente() {
		return renderizaPaginaInexistente;
	}
	public void setRenderizaPaginaInexistente(boolean renderizaPaginaInexistente) {
		this.renderizaPaginaInexistente = renderizaPaginaInexistente;
	}
	
	public boolean isDataFimDisable() {
		return dataFimDisable;
	}
	public void setDataFimDisable(boolean dataFimDisable) {
		this.dataFimDisable = dataFimDisable;
	}
	
	public String getDataAtual() {
		return dataAtual;
	}
	public void setDataAtual(String dataAtual) {
		this.dataAtual = dataAtual;
	}
	
	public List<ResPerAnuncio> getResPerAnuncios() {
		return resPerAnuncios;
	}
	public void setResPerAnuncios(List<ResPerAnuncio> resPerAnuncios) {
		this.resPerAnuncios = resPerAnuncios;
	}
	
	public Long getDiferencaDeDias() {
		return diferencaDeDias;
	}
	public void setDiferencaDeDias(Long diferencaDeDias) {
		this.diferencaDeDias = diferencaDeDias;
	}
}
