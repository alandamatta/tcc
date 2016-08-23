package br.edu.dmsoftware.tcc.bean;


import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Usuario;

@Model
public class ConfirmacaoBean {
	
	private String parametro;
	
	private Usuario usuario = new Usuario();
	
	private Long id;
	
	private String codigo;
	
	private String senha;
	
	private String[] parametroSeparado;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	private Mensagens mensagem;
	
	@Transactional
	public void init() {
		setParametro(parametro);
		separarParametro();
		buscarUsuario();
		confirmarConta();
	}
	
	@Transactional
	public void confirmarConta(){
		if(usuario.getCodVerificacao().equals(codigo)&&(!usuario.isEmailConfirmado())){
			System.out.println("codigo igual");
			usuario.setEmailConfirmado(true);
			usuarioDao.salvar(usuario);
		}else{
			System.out.println("Código diferente ou conta cadastrada");
		}
		System.out.println("PAPAPAAPAPA");
		
	}
	
	public void validaConfirmacao(){
		if(!usuario.getSenha().equals(senha)){
			FacesMessage message = new FacesMessage("Senha incorreta");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public void buscarUsuario(){
		this.usuario = usuarioDao.buscaPeloId(id);
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
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}