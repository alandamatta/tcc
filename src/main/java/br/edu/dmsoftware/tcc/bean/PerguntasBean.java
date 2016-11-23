package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.PerguntaDao;
import br.edu.dmsoftware.tcc.dao.RespostaDao;
import br.edu.dmsoftware.tcc.modelo.Pergunta;
import br.edu.dmsoftware.tcc.modelo.Resposta;

@Model
@ViewScoped
public class PerguntasBean implements Serializable{
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private PerguntaDao perguntaDao;
	private List<Pergunta> perguntas;
	@Inject
	private RespostaDao respostaDao;
	
	private boolean renderedLoader = false;
	private boolean renderedDataScroller = false;
	
	public void init(){
	}
	
	public void carregarPerguntas(){
		
	}
	
	public List<Pergunta> getPerguntas() {
		return perguntas;
	}
	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}
	
	
	
}
