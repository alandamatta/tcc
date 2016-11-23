package br.edu.dmsoftware.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Requisicao;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.StatusRequisicao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class RequisicaoDao extends GenericDaoImp<Requisicao, Long>{
	
	@PersistenceContext
	private EntityManager em;
	
	public boolean existe(Requisicao requisicao){
		String jpql = "select r from Requisicao r where r = :pRequisicao and r.situacao = :pSituacao";
		Query query = em.createQuery(jpql, Requisicao.class);
		query.setParameter("pRequisicao", requisicao);
		query.setParameter("pSituacao", Situacao.ATIVO);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	
	public List<Requisicao> requisicaoNaoNotificadaParaAnunciante(Usuario usuario){
		String jpql = "select r from Requisicao r where r.notificado = false and r.situacao = :pSituacao";
		try {
			Query query = em.createQuery(jpql, Requisicao.class);
			query.setParameter("pSituacao", Situacao.ATIVO);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<Requisicao> porUsuario(Usuario usuario){
		String jpql = "select r from Requisicao r where r.usuario = :pUsuario and r.situacao = :pSituacao";
		try {
			Query query = em.createQuery(jpql, Requisicao.class);
			query.setParameter("pUsuario", usuario);
			query.setParameter("pSituacao", Situacao.ATIVO);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Requisicao> porUsuarioPorStatus(Usuario usuario, StatusRequisicao status, Situacao situacao){
		String jpql = "select r from Requisicao r where r.usuario = :pUsuario and r.status = :pStatus and r.situacao = :pSituacao";
		try {
			Query query = em.createQuery(jpql, Requisicao.class);
			query.setParameter("pUsuario", usuario);
			query.setParameter("pStatus", status);
			query.setParameter("pSituacao", situacao);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Requisicao> porAnunciantePorStatus(Usuario usuario, StatusRequisicao status, Situacao situacao){
		String jpql = "select r from Requisicao r where r.anuncio.usuario = :pUsuario and r.situacao = :pSituacao and r.status = :pStatus";
		try {
			Query query = em.createQuery(jpql, Requisicao.class);
			query.setParameter("pUsuario", usuario);
			query.setParameter("pStatus", status);
			query.setParameter("pSituacao", situacao);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
