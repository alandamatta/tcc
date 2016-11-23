package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Resposta;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class RespostaDao extends GenericDaoImp<Anuncio, Long> implements Serializable{
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Resposta> porAnuncio(Anuncio anuncio){
		String jpql = "select r from Resposta r where r.comentario.anuncio = :pAnuncio";
		try {
			Query query = em.createQuery(jpql, Resposta.class);
			query.setParameter("pAnuncio", anuncio);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
