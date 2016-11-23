package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.PessoaDao;
import br.edu.dmsoftware.tcc.modelo.Pessoa;

@Model
@ViewScoped
public class MenuBean implements Serializable {
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private PessoaDao pessoaDao;
	private boolean msgDadosPessoais = false;
	
	@PostConstruct
	public void init(){
		if(!pessoaDao.pessoaExiste(usuarioLogado.getUsuario())){
			setMsgDadosPessoais(true);
		}
	}
	
	public UsuarioLogadoBean getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(UsuarioLogadoBean usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public boolean isMsgDadosPessoais() {
		return msgDadosPessoais;
	}
	public void setMsgDadosPessoais(boolean msgDadosPessoais) {
		this.msgDadosPessoais = msgDadosPessoais;
	}
}
