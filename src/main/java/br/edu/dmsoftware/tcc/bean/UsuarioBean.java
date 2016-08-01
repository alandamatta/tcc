package br.edu.dmsoftware.tcc.bean;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.CidadeDao;
import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Cidade;
import br.edu.dmsoftware.tcc.modelo.Nivel;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

@Model
public class UsuarioBean {
	
	private Mensagens mensagem = new Mensagens();
	
	private Usuario usuario = new Usuario();
	
	@Inject
	private UsuarioDao usuarioDao;
	
	private List<Usuario> usuarios;
	
	@Inject
	private CidadeDao cidadeDao = new CidadeDao();
	
	private List<Cidade> cidades;
	
	private boolean situacao;
	
	@PostConstruct
	public void init(){
		carregaCidades();
		carregaUsuarios();
	}
	
	@Transactional
	public void salvar(){
		try {
			usuario.setNivel(Nivel.ADMINISTRADOR);
			usuario.setDataCadastro(Calendar.getInstance());
			if(situacao){
				usuario.setSituacao(Situacao.ATIVO);
			}else{
				usuario.setSituacao(Situacao.INATIVO);
			}
			usuarioDao.salvar(usuario);
			limpar();
			carregaUsuarios();
			mensagem.salvoComSucesso();
		} catch (Exception e) {
			mensagem.falhaAoSalvar();
		}
	}
	
	@Transactional
	public void remover(){
		try {
			usuarioDao.remover(usuario);
			limpar();
			carregaUsuarios();
			mensagem.excluidoComSucesso();
		} catch (Exception e) {
			mensagem.falhaAoExcluir();
		}
	}
	
	public void limpar(){
		this.usuario = new Usuario();
	}
	
	public void carregarCampos(Usuario usuario){
		this.usuario = usuario;
	}
	
	private void carregaCidades() {
		this.cidades = cidadeDao.buscarTodos();		
	}
	
	public void carregaUsuarios(){
		this.usuarios = usuarioDao.buscarTodos();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public boolean isSituacao() {
		return situacao;
	}
	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}
	
}
