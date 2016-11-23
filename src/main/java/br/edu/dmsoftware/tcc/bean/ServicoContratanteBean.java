package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.FeedbackDao;
import br.edu.dmsoftware.tcc.dao.PedidoFeedbackDao;
import br.edu.dmsoftware.tcc.dao.ServicoDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Feedback;
import br.edu.dmsoftware.tcc.modelo.PedidoFeedback;
import br.edu.dmsoftware.tcc.modelo.Servico;
import br.edu.dmsoftware.tcc.modelo.Situacao;

@Model
@ViewScoped
public class ServicoContratanteBean implements Serializable{
	
	private Long idServico;
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private Servico servico;
	@Inject
	private ServicoDao servicoDao;
	@Inject
	private PedidoFeedback pedidoFeedback;
	@Inject
	private PedidoFeedbackDao pedidoFeedbackDao;
	private List<PedidoFeedback> pedidosFeedback;
	@Inject
	private Feedback feedback;
	@Inject
	private FeedbackDao feedBackDao;
	private List<Feedback> feedbacks;
	private boolean renderedMensagem = true;
	private boolean renderedServico = false;
	private boolean paginatorPedidoFeedback = false;
	private boolean paginatorFeedbacks = false;
	private Integer activeIndex = 0; 
	private boolean renderedPedidoFeedback = false;
	
	public void preRenderViewMethod(){
		try {
			if(idServico != null){
				carregaServico();
				if((this.servico != null)
					&&(this.servico.getOrcamento().getRequisicao().getUsuario().equals(usuarioLogado.getUsuario()))
					&&(this.servico.getSituacao().equals(Situacao.ATIVO))){
					setRenderedMensagem(false);
					setRenderedServico(true);
					carregarPedidosFeedback();
					verTamanhoPedidoFeedback();
					//O servico contratado é do usuário que está na sessão e o servico está ativo
				}else{
					//servico nao encontrado
					//servico está nulo
					setRenderedMensagem(true);
					setRenderedServico(false);
				}
			}else{
				setRenderedMensagem(true) ;
				setRenderedServico(false);
			}
		} catch (Exception e) {
			
		}
	}
	
	public void pedirFeedback(){
		if(!isRenderedPedidoFeedback()){
			setRenderedPedidoFeedback(true);
			setActiveIndex(1);
		}
	}
	
	public String finalizarServico(){
		return "/contratante/avaliacao.jsf";
	}
	
	public void verTamanhoPedidoFeedback(){
		if(this.pedidosFeedback.size() > 7){
			setPaginatorFeedbacks(true);
		}
	}
	
	@Transactional
	public void enviarPedidoFeedback(){
		try {
			this.pedidoFeedback.setServico(this.servico);
			this.pedidoFeedback.setSituacao(Situacao.ATIVO);
			this.pedidoFeedback.setVisualizada(false);
			this.pedidoFeedbackDao.salvar(this.pedidoFeedback);
			this.limparFeedback();
			//avisar que feedback foi enviado
			carregarPedidosFeedback();
		} catch (Exception e) {
			new Mensagens().acaoEfetuadaComErro();
		}
		
	}
	
	public void carregarPedidosFeedback(){
		this.pedidosFeedback = pedidoFeedbackDao.porUsuarioEServico(this.usuarioLogado.getUsuario(), this.servico);
	}
	
	public void cancelarPedirFeedback(){
		setRenderedPedidoFeedback(false);
		limparFeedback();
	}
	
	public void limparFeedback(){
		this.pedidoFeedback = new PedidoFeedback();
	}
	
	public void carregaServico(){
		this.servico = servicoDao.buscaPeloId(idServico);
	}
	
	public Long getIdServico() {
		return idServico;
	}
	public void setIdServico(Long idServico) {
		this.idServico = idServico;
	}
	
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	public boolean isRenderedMensagem() {
		return renderedMensagem;
	}
	public void setRenderedMensagem(boolean renderedMensagem) {
		this.renderedMensagem = renderedMensagem;
	}
	
	public boolean isRenderedServico() {
		return renderedServico;
	}
	public void setRenderedServico(boolean renderedServico) {
		this.renderedServico = renderedServico;
	}
	
	public PedidoFeedback getPedidoFeedback() {
		return pedidoFeedback;
	}
	public void setPedidoFeedback(PedidoFeedback pedidoFeedback) {
		this.pedidoFeedback = pedidoFeedback;
	}
	
	public Feedback getFeedback() {
		return feedback;
	}
	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
	
	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}
	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	
	public List<PedidoFeedback> getPedidosFeedback() {
		return pedidosFeedback;
	}
	public void setPedidosFeedback(List<PedidoFeedback> pedidosFeedback) {
		this.pedidosFeedback = pedidosFeedback;
	}
	
	public Integer getActiveIndex() {
		return activeIndex;
	}
	public void setActiveIndex(Integer activeIndex) {
		this.activeIndex = activeIndex;
	}
	
	public boolean isPaginatorFeedbacks() {
		return paginatorFeedbacks;
	}
	public void setPaginatorFeedbacks(boolean paginatorFeedbacks) {
		this.paginatorFeedbacks = paginatorFeedbacks;
	}
	
	public boolean isPaginatorPedidoFeedback() {
		return paginatorPedidoFeedback;
	}
	public void setPaginatorPedidoFeedback(boolean paginatorPedidoFeedback) {
		this.paginatorPedidoFeedback = paginatorPedidoFeedback;
	}
	
	public boolean isRenderedPedidoFeedback() {
		return renderedPedidoFeedback;
	}
	public void setRenderedPedidoFeedback(boolean renderedPedidoFeedback) {
		this.renderedPedidoFeedback = renderedPedidoFeedback;
	}
}