package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.OrcamentoDao;
import br.edu.dmsoftware.tcc.dao.RequisicaoDao;
import br.edu.dmsoftware.tcc.dao.ServicoDao;
import br.edu.dmsoftware.tcc.modelo.Orcamento;
import br.edu.dmsoftware.tcc.modelo.Parcela;
import br.edu.dmsoftware.tcc.modelo.Requisicao;
import br.edu.dmsoftware.tcc.modelo.Servico;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.StatusOrcamento;
import br.edu.dmsoftware.tcc.modelo.StatusRequisicao;

@Model
@ViewScoped
public class OrcamentoBean implements Serializable{
	
	
	private Long idOrcamento;
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private Orcamento orcamento;
	@Inject
	private OrcamentoDao orcamentoDao;
	@Inject
	private Requisicao requisicao;
	@Inject
	private RequisicaoDao requisicaoDao;
	@Inject
	private Servico servico;
	@Inject
	private ServicoDao servicoDao;
	
	private boolean renderedOrcamento = false;
	private boolean renderedMensagem = true;
	private boolean renderedParcela = false;
	private Long diferencaDeDias;
	
	public void preRenderViewMethod() {
		
		try {
			if(idOrcamento != null){
				carregarOrcamento();
				if(this.orcamento.getRequisicao().getUsuario().equals(this.usuarioLogado.getUsuario())){
					//orçamento é do usuário
					if(this.orcamento.getRequisicao().getAnuncio().getParcela().equals(Parcela.SIM)){
						setRenderedParcela(true);
					}else{
						setRenderedParcela(false);
					}
					setRenderedOrcamento(true);
					setRenderedMensagem(false);
				}else{
					setRenderedOrcamento(false);
					setRenderedMensagem(true);
				}
			}else{
				setRenderedOrcamento(false);
				setRenderedMensagem(true);
			}
		} catch (Exception e) {
			setRenderedOrcamento(false);
			setRenderedMensagem(true);
		}
		
	}
	
	@Transactional
	public String contratarServico(){
		try {
			requisicao = requisicaoDao.buscaPeloId(this.orcamento.getRequisicao().getId());
			requisicao.setSituacao(Situacao.INATIVO);
			requisicao.setStatus(StatusRequisicao.FINALIZADO_CONTRATADO);
			requisicaoDao.salvar(requisicao);
			orcamento.setSituacao(Situacao.INATIVO);
			orcamento.setStatus(StatusOrcamento.CONTRATADO);
			orcamentoDao.salvar(orcamento);
			servico.setDataDeContratoDoServico(new Date());
			servico.setSituacao(Situacao.ATIVO);
			servico.setOrcamento(orcamento);
			servicoDao.salvar(servico);
			return "";
		} catch (Exception e) {
			return "";
		}
		
	}
	
	public String rejeitarServico(){
		return "";
	}
	
	private void calculaPeriodo(){
		diferencaDeDias = ((this.orcamento.getDataInicio().getTime() - this.orcamento.getDataFim().getTime()) / (1000*60*60*24)) * -1;
	}
	
	public Long getIdOrcamento() {
		return idOrcamento;
	}
	public void setIdOrcamento(Long idOrcamento) {
		this.idOrcamento = idOrcamento;
	}
	
	private void carregarOrcamento(){
		this.orcamento = orcamentoDao.buscaPeloId(this.idOrcamento);
	}
	
	public Orcamento getOrcamento() {
		return orcamento;
	}
	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public boolean isRenderedOrcamento() {
		return renderedOrcamento;
	}
	public void setRenderedOrcamento(boolean renderedOrcamento) {
		this.renderedOrcamento = renderedOrcamento;
	}

	public boolean isRenderedMensagem() {
		return renderedMensagem;
	}
	public void setRenderedMensagem(boolean renderedMensagem) {
		this.renderedMensagem = renderedMensagem;
	}
	
	public boolean isRenderedParcela() {
		return renderedParcela;
	}
	public void setRenderedParcela(boolean renderedParcela) {
		this.renderedParcela = renderedParcela;
	}
}
