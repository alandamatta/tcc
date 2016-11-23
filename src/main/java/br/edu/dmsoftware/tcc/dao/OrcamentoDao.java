package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Comentario;
import br.edu.dmsoftware.tcc.modelo.Orcamento;
import br.edu.dmsoftware.tcc.modelo.Requisicao;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class OrcamentoDao extends GenericDaoImp<Orcamento, Long> implements Serializable{

	@PersistenceContext
	private EntityManager em;
	
	public boolean existe(Orcamento orcamento){
		String jpql = "select o from Orcamento where o = :pOrcamento and o.situacao = :pSituacao";
		Query query = em.createQuery(jpql, Orcamento.class);
		query.setParameter("pOrcamento", orcamento);
		query.setParameter("pSituacao", Situacao.ATIVO);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	public List<Orcamento> porAnunciante(Usuario usuario){
		String jpql = "select o from Orcamento o where o.requisicao.anuncio.usuario = :pUsuario and o.situacao = :pSituacao";
		try {
			Query query = em.createQuery(jpql, Orcamento.class);
			query.setParameter("pUsuario", usuario);
			query.setParameter("pSituacao", Situacao.ATIVO);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<Orcamento> porContratante(Usuario usuario){
		String jpql = "select o from Orcamento o where o.requisicao.usuario = :pUsuario and o.situacao = :pSituacao";
		try {
			Query query = em.createQuery(jpql, Orcamento.class);
			query.setParameter("pUsuario", usuario);
			query.setParameter("pSituacao", Situacao.ATIVO);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
