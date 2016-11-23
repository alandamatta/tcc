package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.CategoriaNv0;
import br.edu.dmsoftware.tcc.modelo.CategoriaNv1;

public class CategoriaNv1Dao extends GenericDaoImp<CategoriaNv1, Long> implements Serializable{
	
	@PersistenceContext
	private EntityManager em;
	
	public List<CategoriaNv1> buscaPorCategoriaPai(CategoriaNv0 categoriaPai){
		try{
			String jpql = "select c from CategoriaNv1 c where c.categoriaPai = :pCategoriaPai";
			Query query = em.createQuery(jpql, CategoriaNv1.class);
			query.setParameter("pCategoriaPai", categoriaPai);
			return query.getResultList();
		}catch(NoResultException e){
			return null;
		}
	}
	
}
