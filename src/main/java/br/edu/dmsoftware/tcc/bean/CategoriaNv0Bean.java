package br.edu.dmsoftware.tcc.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.CategoriaNv0Dao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.CategoriaNv0;

@Model
public class CategoriaNv0Bean {
	
	private Mensagens mensagem = new Mensagens();	
	
	private CategoriaNv0 categoriaNv0 = new CategoriaNv0();
	
	@Inject
	private CategoriaNv0Dao categoriaNv0Dao;
	
	private List<CategoriaNv0> categorias;
	
	@PostConstruct
	public void init() {
		carregaCategorias();
	}

	@Transactional
	public void salvar() {
		try {
			categoriaNv0Dao.salvar(categoriaNv0);
			carregaCategorias();
			limpar();
			mensagem.salvoComSucesso();
		} catch (Exception e) {
			mensagem.falhaAoSalvar();
		}
	}

	@Transactional
	public void remover() {
		try {
			categoriaNv0Dao.remover(categoriaNv0);
			limpar();
			carregaCategorias();
			mensagem.excluidoComSucesso();
		} catch (Exception e) {
			mensagem.falhaAoExcluir();
		}
	}
	
	public void limpar(){
		this.categoriaNv0 = new CategoriaNv0();
	}
	
	public void carregaCampos(CategoriaNv0 categoriaNv0){
		this.categoriaNv0 = categoriaNv0;
	}
	
	public void carregaCategorias(){
		this.categorias = categoriaNv0Dao.buscarTodos();
	}
	
	public CategoriaNv0 getCategoriaNv0() {
		return categoriaNv0;
	}
	public void setCategoriaNv0(CategoriaNv0 categoriaNv0) {
		this.categoriaNv0 = categoriaNv0;
	}
	
	public List<CategoriaNv0> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<CategoriaNv0> categorias) {
		this.categorias = categorias;
	}
	
}
