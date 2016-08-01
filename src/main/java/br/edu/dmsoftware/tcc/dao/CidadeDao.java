package br.edu.dmsoftware.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Cidade;

public class CidadeDao extends GenericDaoImp<Cidade, Long>{
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Cidade> buscaCidadePorEstado(Long id){
		String jpql = "select c from Cidade c where c.estado.id = :pEstado";
		try {
			Query query = em.createQuery(jpql, Cidade.class);
			query.setParameter("pEstado", id);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
