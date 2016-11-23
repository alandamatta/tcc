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
import br.edu.dmsoftware.tcc.modelo.ComentarioAvaliacaoAnuncio;
import br.edu.dmsoftware.tcc.modelo.Recomenda;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class ComentarioAvaliacaoAnuncioDao extends GenericDaoImp<ComentarioAvaliacaoAnuncio, Long> implements Serializable{
	
	@PersistenceContext
	EntityManager em;
	
	public List<ComentarioAvaliacaoAnuncio> positivoPorAnuncio(Anuncio anuncio){
		String jpql = "select c from ComentarioAvaliacaoAnuncio c where c.avaliacaoAnuncio.servico.orcamento.requisicao.anuncio = :pAnuncio and c.avaliacaoAnuncio.recomenda = :pRecomenda";
		try {
			Query query = em.createQuery(jpql, ComentarioAvaliacaoAnuncio.class);
			query.setParameter("pRecomenda", Recomenda.SIM);
			query.setParameter("pUsuario", anuncio);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<ComentarioAvaliacaoAnuncio> NegativoPorAnuncio(Anuncio anuncio){
		String jpql = "select c from ComentarioAvaliacaoAnuncio c where c.avaliacaoAnuncio.servico.orcamento.requisicao.anuncio = :pAnuncio and c.avaliacaoAnuncio.recomenda = :pRecomenda";
		try {
			Query query = em.createQuery(jpql, ComentarioAvaliacaoAnuncio.class);
			query.setParameter("pRecomenda", Recomenda.NAO);
			query.setParameter("pUsuario", anuncio);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
}
