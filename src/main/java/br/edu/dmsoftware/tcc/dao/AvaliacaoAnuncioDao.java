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
import br.edu.dmsoftware.tcc.modelo.AvaliacaoAnuncio;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class AvaliacaoAnuncioDao extends GenericDaoImp<AvaliacaoAnuncio, Long> implements Serializable {

	@PersistenceContext
	private EntityManager em;

	public Number totalEstrelasPorAnuncio(Anuncio anuncio) {
		Query query = em.createQuery("select sum(a.numeroDeEstrelas) from AvaliacaoAnuncio a where a.servico.orcamento.requisicao.anuncio = :pAnuncio");
		return (Number) query.getSingleResult();
	}

	public List<AvaliacaoAnuncio> todosPorAnuncio(Anuncio anuncio) {
		String jpql = "select a from AvaliacaoAnuncio a where a.servico.orcamento.requisicao.anuncio = :pAnuncio";
		try {
			Query query = em.createQuery(jpql, AvaliacaoAnuncio.class);
			query.setParameter("pAnuncio", anuncio);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<AvaliacaoAnuncio> porAnuncioPorNumeroDeEstrelas(Anuncio anuncio, Integer numeroDeEstrelas) {
		String jpql = "select a from AvaliacaoAnuncio a where a.servico.orcamento.requisicao.anuncio = :pAnuncio and a.numeroDeEstrelas = :pNumeroDeEstrelas";
		try {
			Query query = em.createQuery(jpql, AvaliacaoAnuncio.class);
			query.setParameter("pAnuncio", anuncio);
			query.setParameter("pNumeroDeEstrelas", numeroDeEstrelas);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
