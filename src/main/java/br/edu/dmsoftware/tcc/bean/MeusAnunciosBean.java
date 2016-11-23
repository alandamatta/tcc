package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.AnuncioDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Situacao;
@ViewScoped
@Model
public class MeusAnunciosBean implements Serializable{
	@Inject
	private Anuncio anuncio;
	@Inject
	private AnuncioDao anuncioDao;
	private List<Anuncio> anuncios;
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	
	public void init(){
		carregarAnuncios();
	}
	
	@Transactional
	public void excluir(Anuncio anuncio){
		try {
			anuncio.setSituacao(Situacao.INATIVO);
			anuncioDao.salvar(anuncio);
			carregarAnuncios();
		} catch (Exception e) {
			new Mensagens().falhaAoExcluir();
		}
	}
	
	@Transactional
	public void pausar(Anuncio anuncio){
		try {
			if(anuncio.getSituacao() == Situacao.PAUSADO){
				anuncio.setSituacao(Situacao.ATIVO);
				anuncioDao.salvar(anuncio);
			}else if(anuncio.getSituacao() == Situacao.ATIVO){
				anuncio.setSituacao(Situacao.PAUSADO);
				anuncioDao.salvar(anuncio);
			}
			carregarAnuncios();
		} catch (Exception e) {
			new Mensagens().falhaAoExcluir();
		}
	}
	
	public void carregarAnuncios(){
		anuncios = anuncioDao.buscaPorUsuario(usuarioLogado.getUsuario());
	}
	
	public List<Anuncio> getAnuncios() {
		return anuncios;
	}
	public void setAnuncios(List<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}
	
}
