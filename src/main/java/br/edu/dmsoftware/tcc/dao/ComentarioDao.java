package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Comentario;

public class ComentarioDao extends GenericDaoImp<Comentario, Long> implements Serializable{
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Comentario> buscaComentarioPorAnuncio(Anuncio anuncio){
		String jpql = "select c from Comentario c where c.anuncio = :pAnuncio";
		try {
			Query query = em.createQuery(jpql, Comentario.class);
			query.setParameter("pAnuncio", anuncio);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
}
