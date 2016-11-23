package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.AnuncioDao;
import br.edu.dmsoftware.tcc.dao.FavoritoDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Favorito;

@Model
@ViewScoped
public class PesquisaBean implements Serializable{

	private String pesquisa;
	@Inject
	private Anuncio anuncio;
	@Inject
	private AnuncioDao anuncioDao;
	private List<Anuncio> anuncios;
	@Inject
	private Favorito favorito;
	@Inject
	private FavoritoDao favoritoDao;
	
	private String valueBtnLogin;
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	
	public void buscarAnuncios(){
		if(this.pesquisa != null){
			this.anuncios = anuncioDao.pesquisarPorTitulo(pesquisa);
		}
		if(usuarioLogado.isLogado()){
			setValueBtnLogin("Sair");
		}else{
			setValueBtnLogin("Login");
		}
	}
	
	public String pesquisar(){
		return "/pesquisa.jsf?faces-redirect=true&pesquisa="+this.pesquisa;
	}
	
	public void carregarAnuncios(){
		anuncios = anuncioDao.pesquisarPorTitulo(pesquisa);
	}
	
	public List<Anuncio> autoCompleteAnuncio(String pesquisa){
		System.out.println(pesquisa);
		anuncios = anuncioDao.pesquisarPorTitulo(pesquisa);
		return anuncioDao.pesquisarPorTitulo(pesquisa);
	}
	
	@Transactional
	public String adicionarFavorito(Anuncio anuncio){
		if(usuarioLogado.isLogado()){
			if(favoritoDao.favoritoExiste(anuncio, usuarioLogado.getUsuario())){
				new Mensagens().anuncioJaFavoritado();
				return "";
			}
			favorito.setAnuncioFavoritado(anuncio);
			favorito.setDataAdicionado(Calendar.getInstance());
			favorito.setUsuario(usuarioLogado.getUsuario());
			try {
				favoritoDao.salvar(favorito);
				new Mensagens().anuncioAddAosFavoritos();
				return "";
			} catch (Exception e) {
				new Mensagens().erro();
				return "";
			}
		}else{
			new Mensagens().usuarioNaoLogado();
			return "";
		}
	}
	
	public String acaoBtnLogar(){
		if(usuarioLogado.isLogado()){
			return "/menu.jsf?faces-redirect=true";
		}else{
			return "/login.jsf?faces-redirect=true";
		}
	}
	
	
	
	public String getPesquisa() {
		return pesquisa;
	}
	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
	
	public Anuncio getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	
	public List<Anuncio> getAnuncios() {
		return anuncios;
	}
	public void setAnuncios(List<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}
	
	public String getValueBtnLogin() {
		return valueBtnLogin;
	}
	public void setValueBtnLogin(String valueBtnLogin) {
		this.valueBtnLogin = valueBtnLogin;
	}
	
}
