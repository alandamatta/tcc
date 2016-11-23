package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.OrcamentoDao;
import br.edu.dmsoftware.tcc.modelo.Orcamento;

@Model
public class OrcamentosBean implements Serializable{
	
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	
	@Inject
	private OrcamentoDao orcamentoDao;
	private List<Orcamento> orcamentos;
	
	private boolean renderedDataScroller = false;
	private boolean renderedLoader = false;
	
	@PostConstruct
	public void init(){
		carregaOrcamentos();
		if(this.orcamentos.size() > 7){
			this.renderedLoader = true;
		}else if(!this.orcamentos.isEmpty()){
			this.renderedDataScroller = true;
		}
	}
	
	public void carregaOrcamentos(){
		this.orcamentos = orcamentoDao.porContratante(usuarioLogado.getUsuario());
	}
	
	public List<Orcamento> getOrcamentos() {
		return orcamentos;
	}
	public void setOrcamentos(List<Orcamento> orcamentos) {
		this.orcamentos = orcamentos;
	}
	
	public boolean isRenderedDataScroller() {
		return renderedDataScroller;
	}
	public void setRenderedDataScroller(boolean renderedDataScroller) {
		this.renderedDataScroller = renderedDataScroller;
	}
	
	public boolean isRenderedLoader() {
		return renderedLoader;
	}
	public void setRenderedLoader(boolean renderedLoader) {
		this.renderedLoader = renderedLoader;
	}
	
}
