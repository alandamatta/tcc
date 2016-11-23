package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Pergunta;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class PerguntaDao extends GenericDaoImp<Pergunta, Long> implements Serializable{
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Pergunta> perguntasDoAnuncio(Anuncio anuncio){
		String jpql = "select p from Pergunta p where p.anuncio = :pAnuncio and p.ativa = true";
		try {
			Query query = em.createQuery(jpql, Pergunta.class);
			query.setParameter("pAnuncio", anuncio);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Pergunta> perguntasDoAnunciante(Usuario usuario){
		String jpql = "select p from Pergunta p where p.anuncio.usuario = :pUsuario and p.ativa = true";
		try {
			Query query = em.createQuery(jpql, Pergunta.class);
			query.setParameter("pUsuario", usuario);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
