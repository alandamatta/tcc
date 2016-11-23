package br.edu.dmsoftware.tcc.bean;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.CidadeDao;
import br.edu.dmsoftware.tcc.dao.EstadoDao;
import br.edu.dmsoftware.tcc.dao.PessoaDao;
import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Cidade;
import br.edu.dmsoftware.tcc.modelo.Estado;
import br.edu.dmsoftware.tcc.modelo.Pessoa;
import br.edu.dmsoftware.tcc.modelo.Usuario;

@Model
public class PessoaBean {
	
	private Pessoa pessoa = new Pessoa();
	@Inject
	private PessoaDao pessoaDao;
	private List<Pessoa> pessoas;
	
	private Usuario usuario = new Usuario();
	@Inject
	private UsuarioDao usuarioDao;
	
	private Mensagens mensagem;
	
	private List<Estado> estados;
	@Inject
	private EstadoDao estadoDao;
	private Estado estado = new Estado();
	
	private List<Cidade> cidades;
	@Inject
	private CidadeDao cidadeDao;
	
	
	
	@PostConstruct
	public void init(){
		carregaEstados();
		cidades = cidadeDao.buscarTodos();
	}
	
	public void salvarUsuario(){
		try{
			pessoa.setDataCadastro(Calendar.getInstance());
			pessoa.setDadosCompletos(100);
			pessoaDao.salvar(pessoa);
			usuario.setPessoa(pessoa);
			usuarioDao.salvar(usuario);
			mensagem.salvoComSucesso();
		}catch (Exception e)  {
			mensagem.falhaAoSalvar();
		}
	}
	
	public void remover(){
		
	}
	
	public void limpaPessoa(){
		this.pessoa = new Pessoa();
	}
	
	public void limpaUsuario(){
		this.usuario = new Usuario();
	}
	
	public void carregaEstados(){
		this.estados = estadoDao.buscarTodos();
	}
	
	public void carregaCidades(){
		this.cidades = cidadeDao.buscaCidadePorEstado(estado);
	}
	
	public void carregarPessoa(){
		
	}
	
	public void carregarUsuario(){
		
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
