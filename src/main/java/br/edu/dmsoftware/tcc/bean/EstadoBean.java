package br.edu.dmsoftware.tcc.bean;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.EstadoDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Estado;
@Model
public class EstadoBean{
	
	private Estado estado = new Estado();
	
	@Inject
	private EstadoDao estadoDao;
	
	private Mensagens mensagem = new Mensagens();
	
	private List<Estado> estados;
	
	@PostConstruct
	public void init(){
		carregarEstados();
	}
	
	@Transactional
	public void salvar(){
		try {
			estadoDao.salvar(estado);
			limpar();
			carregarEstados();
			mensagem.salvoComSucesso();
		} catch (Exception e) {
			mensagem.falhaAoSalvar();
		}
	}
	
	@Transactional
	public void remover(){
		try {
			estadoDao.remover(estado);
			limpar();
			carregarEstados();
			mensagem.excluidoComSucesso();
		} catch (Exception e) {
			mensagem.falhaAoExcluir();
		}
	}
	
	public void carregaCampos(Estado estado){
		this.estado = estado;
	}
	
	public void carregarEstados(){
		estados = estadoDao.buscarTodos();
	}
	
	public void limpar(){
		estado = new Estado();
	}
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
}
