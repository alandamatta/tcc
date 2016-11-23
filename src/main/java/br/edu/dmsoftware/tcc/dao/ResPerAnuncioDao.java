package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Requisicao;
import br.edu.dmsoftware.tcc.modelo.ResPerAnuncio;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class ResPerAnuncioDao extends GenericDaoImp<ResPerAnuncio, Long> implements Serializable{
	
	@PersistenceContext
	private EntityManager em;
	
	public List<ResPerAnuncio> porRequisicao(Requisicao requisicao){
		String jpql = "select r from ResPerAnuncio r where r.requisicao = :pRequisicao";
		try {
			Query query = em.createQuery(jpql, ResPerAnuncio.class);
			query.setParameter("pRequisicao", requisicao);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
}
