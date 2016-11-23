package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.RequisicaoDao;
import br.edu.dmsoftware.tcc.modelo.Requisicao;

@Model
public class RequisicaoFeitaBean implements Serializable{
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	private List<Requisicao> requisicoes;
	@Inject
	private RequisicaoDao requisicaoDao;
	private boolean mostraLoader = false;
	
	@PostConstruct
	public void init(){
		carregaRequisicoes();
		if(requisicoes.size() > 7){
			setMostraLoader(true);
		}else{
			setMostraLoader(false);
		}
	}
	
	public void carregaRequisicoes(){
		requisicoes = requisicaoDao.porUsuario(usuarioLogado.getUsuario());
	}
	
	public List<Requisicao> getRequisicoes() {
		return requisicoes;
	}
	public void setRequisicoes(List<Requisicao> requisicoes) {
		this.requisicoes = requisicoes;
	}
	
	public boolean isMostraLoader() {
		return mostraLoader;
	}
	public void setMostraLoader(boolean mostraLoader) {
		this.mostraLoader = mostraLoader;
	}
	
}
