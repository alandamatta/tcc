package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Comentario;
import br.edu.dmsoftware.tcc.modelo.Feedback;
import br.edu.dmsoftware.tcc.modelo.PedidoFeedback;
import br.edu.dmsoftware.tcc.modelo.Requisicao;
import br.edu.dmsoftware.tcc.modelo.Servico;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

public class ServicoDao extends GenericDaoImp<Servico, Long> implements Serializable{
	
	@PersistenceContext
	EntityManager em;
	
	public List<Servico> porContratante(Usuario usuario){
		String jpql = "select s from Servico s where s.orcamento.requisicao.usuario = :pUsuario and s.situacao = :pSituacao";
		Query query = em.createQuery(jpql, Servico.class);
		query.setParameter("pUsuario", usuario);
		query.setParameter("pSituacao", Situacao.ATIVO);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Servico> porAnunciante(Usuario usuario){
		String jpql = "select s from Servico s where s.orcamento.requisicao.anuncio.usuario = :pUsuario and s.situacao = :pSituacao";
		Query query = em.createQuery(jpql, Servico.class);
		query.setParameter("pUsuario", usuario);
		query.setParameter("pSituacao", Situacao.ATIVO);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
