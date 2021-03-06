package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.MD5Crypt;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Usuario;

@Model
public class LoginBean implements Serializable{
	
	private String usuario;
	private String senha;
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	
	public String autenticar(){
		try {
			if(usuarioDao.usuarioExiste(this.usuario)){
				Usuario usuario = usuarioDao.buscaPorUsuario(this.usuario) ;
				if(usuario.getUsuario().equals(this.usuario) && 
						(usuario.getSenha().equals(new MD5Crypt().criptografar(this.senha))) && (usuario.isEmailConfirmado())){
					//logado
					usuarioLogado.logar(usuario);
					new Mensagens().logadoComSucesso();
					return "logado/menu.jsf?faces-redirect=true";
					}else if(!usuario.isEmailConfirmado()){
					//email não confirmado
					new Mensagens().contaNaoConfirmada();
					return "login.jsf";
				}else{
					//usuario ou senha incorretos
					new Mensagens().falhaAutenticacao();
					return "login.jsf";
				}
			}else{
				//usuário nao existe
				new Mensagens().falhaAutenticacao();
				return "login.jsf";
			}
		} catch (Exception e) {
			new Mensagens().falhaAutenticacao();
			return "login.jsf";
		}
	}
	
	public void validateUsuario(FacesContext context, UIComponent toValidate, Object value){
		String usuario = (String) value;
		if(usuario.equals(null) || (usuario.trim().equals(""))){
			FacesMessage message = new FacesMessage("Usuário deve estar preenchido");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public void validateSenha(FacesContext context, UIComponent toValidate, Object value){
		String senha = (String) value;
		if(senha.equals(null) || senha.trim().equals("")){
			FacesMessage message = new FacesMessage("Senha deve estar preenchida");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
