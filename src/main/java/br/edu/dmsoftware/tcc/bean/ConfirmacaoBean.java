package br.edu.dmsoftware.tcc.bean;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.MD5Crypt;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Usuario;

@Model
@ViewScoped
public class ConfirmacaoBean implements Serializable{
	
	private String parametro;
	
	private Usuario usuario = new Usuario();
	
	private Long id;
	
	private String codigo;
	
	private String senha;
	
	private String[] parametroSeparado;
	
	private boolean rendered = false;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Transactional
	public String confirmar(){
		if(usuario.getSenha().equals(new MD5Crypt().criptografar(senha))){
			usuario.setEmailConfirmado(true);
			usuarioDao.salvar(usuario);
			new Mensagens().confirmadoSucesso();
			return "/login.jsf";
		}else{
			new Mensagens().senhaIncorreta();
			return "";
		}
	}
	
	public void carregaUsuario(){
		separarParametro();
		try {
			usuario = usuarioDao.buscaPeloId(id);
			if(usuario.getCodVerificacao().equals(codigo) && (!usuario.isEmailConfirmado())){
				setRendered(true);
			}else if(usuario.getCodVerificacao().equals(codigo) && (usuario.isEmailConfirmado())){
				new Mensagens().contaConfirmada();
			}else{
				setRendered(false);
				new Mensagens().paginaNaoEncontrada();
			}
		} catch (Exception e) {
			setRendered(false);
			new Mensagens().paginaNaoEncontrada();
		}
	}
	
	public void separarParametro(){
		parametroSeparado = parametro.split("W");
		id = Long.parseLong(parametroSeparado[0]);
		codigo = parametroSeparado[1];
	}
	
	public String getParametro() {
		return parametro;
	}
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean isRendered() {
		return rendered;
	}
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}