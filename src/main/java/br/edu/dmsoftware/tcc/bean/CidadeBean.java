package br.edu.dmsoftware.tcc.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.CidadeDao;
import br.edu.dmsoftware.tcc.dao.EstadoDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Cidade;
import br.edu.dmsoftware.tcc.modelo.Estado;

@Model
public class CidadeBean {

	private Cidade cidade = new Cidade();

	@Inject
	private CidadeDao cidadeDao;
	
	@Inject
	private EstadoDao estadoDao;

	private Mensagens mensagem = new Mensagens();

	private List<Estado> estados;
	
	private List<Cidade> cidades;

	@PostConstruct
	public void init() {
		carregarEstados();
		carregarCidades();
	}

	@Transactional
	public void salvar() {
		try {
			cidadeDao.salvar(cidade);
			limpar();
			carregarCidades();
			mensagem.salvoComSucesso();
		} catch (Exception e) {
			mensagem.falhaAoSalvar();
		}
	}

	@Transactional
	public void remover() {
		try {
			cidadeDao.remover(cidade);
			limpar();
			carregarCidades();
			mensagem.excluidoComSucesso();
		} catch (Exception e) {
			mensagem.falhaAoExcluir();
		}
	}
	
	public void limpar(){
		this.cidade = new Cidade();
	}
	
	public void carregaCampos(Cidade cidade){
		this.cidade = cidade;
	}
	
	public void carregarEstados(){
		estados = estadoDao.buscarTodos();
	}
	
	public void carregarCidades(){
		cidades = cidadeDao.buscarTodos();
	}
	
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
}
