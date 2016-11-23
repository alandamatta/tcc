package br.edu.dmsoftware.tcc.bean;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;

import br.edu.dmsoftware.tcc.modelo.Usuario;
import java.io.Serializable;
@SessionScoped
@Model
public class UsuarioLogadoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	
	public void logar(Usuario usuario){
		this.usuario = usuario;
	}
	
	public void deslogar(){
		this.usuario = null;
	}
	
	public boolean isLogado(){
		return this.usuario != null;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
