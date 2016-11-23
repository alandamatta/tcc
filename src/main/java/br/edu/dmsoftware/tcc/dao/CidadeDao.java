package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Cidade;
import br.edu.dmsoftware.tcc.modelo.Estado;

public class CidadeDao extends GenericDaoImp<Cidade, Long> implements Serializable{
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Cidade> buscaCidadePorEstado(Estado estado){
		String jpql = "select c from Cidade c where c.estado = :pEstado";
		try {
			Query query = em.createQuery(jpql, Cidade.class);
			query.setParameter("pEstado", estado);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
