package br.edu.dmsoftware.tcc.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Pessoa;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class PessoaDao extends GenericDaoImp<Pessoa, Long>{
	
	@PersistenceContext
	EntityManager em;
	
	public boolean pessoaExiste(Usuario usuario){
		String jpql = "select p from Pessoa p where p.usuario = :pUsuario";
		Query query = em.createQuery(jpql, Pessoa.class);
		query.setParameter("pUsuario", usuario);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}		
	}
	
}
