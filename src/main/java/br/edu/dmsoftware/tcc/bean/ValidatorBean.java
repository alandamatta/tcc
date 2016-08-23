package br.edu.dmsoftware.tcc.bean;

import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.UsuarioDao;

public class ValidatorBean {
	
	@Inject
	private UsuarioDao usuarioDao;
	
	public boolean emailUnico(String email){
		return usuarioDao.emailExiste(email);
	}
	
	public boolean usuarioUnico(String usuario){
		return usuarioDao.usuarioExiste(usuario);
	}
	
}
