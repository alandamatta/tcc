package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.FacesUtil;
import br.edu.dmsoftware.tcc.modelo.Usuario;
@Model
@ViewScoped
public class ConfirmacaoBean2 implements Serializable{
	@Inject
	private Usuario usuario;
	@Inject
	private UsuarioDao usuarioDao;
	
	public void carregaUsuario(){
		String valor = FacesUtil.getParam("C");
		if(valor != null){
			Long id = Long.parseLong(valor);
			usuario = usuarioDao.buscaPeloId(id);
		}
	}
	
	public void teste(){
		System.out.println("Usuario: " + usuario.getCodVerificacao());
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
