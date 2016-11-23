package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.ServicoDao;
import br.edu.dmsoftware.tcc.modelo.Servico;

@Model
@ViewScoped
public class ServicosContratanteBean implements Serializable{
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private ServicoDao servicoDao;
	private List<Servico> servicos;
	
	@PostConstruct
	public void init(){
		carregarServicos();
	}
	
	public void carregarServicos(){
		this.servicos = servicoDao.porContratante(usuarioLogado.getUsuario());
	}
	
	public List<Servico> getServicos() {
		return servicos;
	}
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	
}
