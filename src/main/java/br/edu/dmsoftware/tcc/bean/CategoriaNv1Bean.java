package br.edu.dmsoftware.tcc.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.CategoriaNv0Dao;
import br.edu.dmsoftware.tcc.dao.CategoriaNv1Dao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.CategoriaNv0;
import br.edu.dmsoftware.tcc.modelo.CategoriaNv1;

@Model
public class CategoriaNv1Bean {
	
	private Mensagens mensagem = new Mensagens();	
	
	private CategoriaNv1 categoriaNv1 = new CategoriaNv1();
	
	@Inject
	private CategoriaNv1Dao categoriaNv1Dao;
	
	private List<CategoriaNv1> categorias;
	
	@Inject
	private CategoriaNv0Dao categoriaNv0Dao;
	
	private List<CategoriaNv0> categoriasPai;
	
	@PostConstruct
	public void init() {
		carregaCategorias();
		carregaCategoriasPai();
	}

	@Transactional
	public void salvar() {
		try {
			categoriaNv1Dao.salvar(categoriaNv1);
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
			categoriaNv1Dao.remover(categoriaNv1);
			limpar();
			carregaCategorias();
			mensagem.excluidoComSucesso();
		} catch (Exception e) {
			mensagem.falhaAoExcluir();
		}
	}
	
	public void limpar(){
		this.categoriaNv1 = new CategoriaNv1();
	}
	
	public void carregaCampos(CategoriaNv1 categoriaNv1){
		this.categoriaNv1 = categoriaNv1;
	}
	
	public void carregaCategorias(){
		this.categorias = categoriaNv1Dao.buscarTodos();
	}
	
	public void carregaCategoriasPai(){
		this.categoriasPai = this.categoriaNv0Dao.buscarTodos();
	}
	
	public CategoriaNv1 getCategoriaNv1() {
		return categoriaNv1;
	}
	public void setCategoriaNv1(CategoriaNv1 categoriaNv1) {
		this.categoriaNv1 = categoriaNv1;
	}
	
	public List<CategoriaNv1> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<CategoriaNv1> categorias) {
		this.categorias = categorias;
	}
	
	public List<CategoriaNv0> getCategoriasPai() {
		return categoriasPai;
	}
	public void setCategoriasPai(List<CategoriaNv0> categoriasPai) {
		this.categoriasPai = categoriasPai;
	}
}
