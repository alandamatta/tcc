package br.edu.dmsoftware.tcc.bean;

import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Pessoa;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class UsuarioBean {
	Mensagens mensagem = new Mensagens();
	Usuario usuario = new Usuario();
	Pessoa pessoa = new Pessoa();
	
	@Inject
	UsuarioDao usuarioDao;
	
	public void salvar(){
		try {
			usuarioDao.salvar(usuario);
			mensagem.salvoComSucesso();
		} catch (Exception e) {
			mensagem.falhaAoSalvar();
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
