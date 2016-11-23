package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.BusyConversationException;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.AvaliacaoAnuncioDao;
import br.edu.dmsoftware.tcc.dao.ComentarioAvaliacaoAnuncioDao;
import br.edu.dmsoftware.tcc.dao.ServicoDao;
import br.edu.dmsoftware.tcc.modelo.AvaliacaoAnuncio;
import br.edu.dmsoftware.tcc.modelo.ComentarioAvaliacaoAnuncio;
import br.edu.dmsoftware.tcc.modelo.Recomenda;
import br.edu.dmsoftware.tcc.modelo.Servico;
import br.edu.dmsoftware.tcc.modelo.Situacao;

@Model
@ViewScoped
public class AvaliaAnuncianteBean implements Serializable{
	
	private Long idServico;
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private Servico servico;
	@Inject
	private ServicoDao servicoDao;
	@Inject
	private AvaliacaoAnuncio avaliacaoAnuncio;
	@Inject
	private AvaliacaoAnuncioDao avaliacaoAnuncioDao;
	@Inject
	private ComentarioAvaliacaoAnuncio comentarioAvaliacaoAnuncio;
	@Inject
	private ComentarioAvaliacaoAnuncioDao comentarioAvaliacaoAnuncioDao;
	
	private boolean renderizaMsgPaginaInexistente = true;
	private boolean renderizaPagina = false;
	
	public void preRenderViewMethod(){
		if(idServico != null){
			try {
				carregaServico();
				if((this.servico.getOrcamento().getRequisicao().getUsuario().equals(this.usuarioLogado.getUsuario()))
						&&(this.servico.getSituacao().equals(Situacao.ATIVO))){
					setRenderizaMsgPaginaInexistente(false);
					setRenderizaPagina(true);
				}else{
					//servico nao é do usuário que está tentando acessar
					setRenderizaMsgPaginaInexistente(true);
					setRenderizaPagina(false);
					
				}
			} catch (Exception e) {
				//não encontrou o servico
				setRenderizaMsgPaginaInexistente(true);
				setRenderizaPagina(false);
			}
			
		}else{
			setRenderizaMsgPaginaInexistente(true);
			setRenderizaPagina(false);
		}
	}
	
	public void carregaServico(){
		this.servico = servicoDao.buscaPeloId(idServico);
	}
	
	@Transactional
	public String salvar(){
		try {
			this.avaliacaoAnuncio.setServico(this.servico);
			AvaliacaoAnuncio avaliacaoAnuncioTemp = this.avaliacaoAnuncioDao.salvar(avaliacaoAnuncio);
			
			if(this.comentarioAvaliacaoAnuncio.getComentario() != null){
				this.comentarioAvaliacaoAnuncio.setDataAvaliacao(Calendar.getInstance());
				this.comentarioAvaliacaoAnuncio.setAvaliacaoAnuncio(avaliacaoAnuncioTemp);
				this.comentarioAvaliacaoAnuncioDao.salvar(this.comentarioAvaliacaoAnuncio);
			}
			this.servico.setDataDoServicoFinalizado(new Date());
			this.servico.setSituacao(Situacao.INATIVO);
			this.servicoDao.salvar(this.servico);
			
			return "/logado/menu.jsf";
		} catch (Exception e) {
			return "";
		}
		
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

	public boolean isRenderizaMsgPaginaInexistente() {
		return renderizaMsgPaginaInexistente;
	}
	public void setRenderizaMsgPaginaInexistente(boolean renderizaMsgPaginaInexistente) {
		this.renderizaMsgPaginaInexistente = renderizaMsgPaginaInexistente;
	}

	public boolean isRenderizaPagina() {
		return renderizaPagina;
	}
	public void setRenderizaPagina(boolean renderizaPagina) {
		this.renderizaPagina = renderizaPagina;
	}
	
	public Recomenda[] getRecomenda(){
		return Recomenda.values();
	}

	public AvaliacaoAnuncio getAvaliacaoAnuncio() {
		return avaliacaoAnuncio;
	}

	public void setAvaliacaoAnuncio(AvaliacaoAnuncio avaliacaoAnuncio) {
		this.avaliacaoAnuncio = avaliacaoAnuncio;
	}
	
	public ComentarioAvaliacaoAnuncio getComentarioAvaliacaoAnuncio() {
		return comentarioAvaliacaoAnuncio;
	}
	public void setComentarioAvaliacaoAnuncio(ComentarioAvaliacaoAnuncio comentarioAvaliacaoAnuncio) {
		this.comentarioAvaliacaoAnuncio = comentarioAvaliacaoAnuncio;
	}
	
	public AvaliacaoAnuncioDao getAvaliacaoAnuncioDao() {
		return avaliacaoAnuncioDao;
	}
	public void setAvaliacaoAnuncioDao(AvaliacaoAnuncioDao avaliacaoAnuncioDao) {
		this.avaliacaoAnuncioDao = avaliacaoAnuncioDao;
	}
}
