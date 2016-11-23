package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.FavoritoDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Favorito;
@Model
@ViewScoped
public class FavoritoBean implements Serializable {
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private FavoritoDao favoritoDao;
	private List<Favorito> favoritos;
	
	public void carregarFavoritos(){
		this.favoritos = favoritoDao.favoritosPorUsuario(usuarioLogado.getUsuario());
	}
	
	@Transactional
	public void remover(Favorito favorito){
		try {
			favoritoDao.remover(favorito);
		} catch (Exception e) {
			new Mensagens().falhaAoExcluir();
		}
	}
	
	public List<Favorito> getFavoritos() {
		return favoritos;
	}
	public void setFavoritos(List<Favorito> favoritos) {
		this.favoritos = favoritos;
	}
	
}
