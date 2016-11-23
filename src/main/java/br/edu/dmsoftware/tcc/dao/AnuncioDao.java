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
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class AnuncioDao extends GenericDaoImp<Anuncio, Long> implements Serializable{
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Anuncio> buscaPorUsuario(Usuario usuario){
		String jpql = "select a from Anuncio a where a.usuario = :pUsuario and a.situacao != :pSituacao";
		try {
			Query query = em.createQuery(jpql, Anuncio.class);
			query.setParameter("pSituacao", Situacao.INATIVO);
			query.setParameter("pUsuario", usuario);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Anuncio> pesquisarPorTitulo(String titulo){
		String jpql = "select a from Anuncio a where a.titulo like :pTitulo and a.situacao != :pSituacao";
		try {
			Query query = em.createQuery(jpql, Anuncio.class);
			query.setParameter("pTitulo", '%' + titulo + '%');
			query.setParameter("pSituacao", Situacao.INATIVO);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
//	public List<Anuncio> pesquisarPorTitulo(String titulo){
//		CriteriaBuilder builder = em.getCriteriaBuilder();
//		CriteriaQuery<Anuncio> criteriaQuery = builder.createQuery(Anuncio.class);
//		Root<Anuncio> a = criteriaQuery.from(Anuncio.class);
//		criteriaQuery.select(a);
//		if((StringUtils.isNotBlank(titulo)) && (StringUtils.isNotEmpty(titulo))){
//			criteriaQuery.where(builder.like(a.<String>get("titulo"), "%" + titulo + "%"));
//			return em.createQuery(criteriaQuery).getResultList();
//		}
//		return null;
//	}
	
}
