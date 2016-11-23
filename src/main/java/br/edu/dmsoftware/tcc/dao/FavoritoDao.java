package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Favorito;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class FavoritoDao extends GenericDaoImp<Favorito, Long> implements Serializable{
	
	@PersistenceContext
	private EntityManager em;
	
	public boolean favoritoExiste(Anuncio anuncio, Usuario usuario){
		String jpql = "select f from Favorito f where f.anuncioFavoritado = :pAnuncio and f.usuario = :pUsuario";
		Query query = em.createQuery(jpql, Favorito.class);
		query.setParameter("pAnuncio", anuncio);
		query.setParameter("pUsuario", usuario);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	public List<Favorito> favoritosPorUsuario(Usuario usuario){
		String jpql = "select f from Favorito f where f.usuario = :pUsuario";
		Query query = em.createQuery(jpql, Favorito.class);
		query.setParameter("pUsuario", usuario);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
