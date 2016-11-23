package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.ImagemAnuncio;

public class ImagemAnuncioDao extends GenericDaoImp<ImagemAnuncio, Long> implements Serializable{
	
	@PersistenceContext
	private EntityManager em;
	
	public List<ImagemAnuncio> porAnuncio(Anuncio anuncio){
		String jpql = "select i from ImagemAnuncio i where i.anuncio = :pAnuncio";
		try {
			Query query = em.createQuery(jpql, ImagemAnuncio.class);
			query.setParameter("pAnuncio", anuncio);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
