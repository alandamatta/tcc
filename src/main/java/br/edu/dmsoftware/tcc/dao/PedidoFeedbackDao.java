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

public class PedidoFeedbackDao extends GenericDaoImp<PedidoFeedback, Long> implements Serializable{
	
	@PersistenceContext
	private EntityManager em;
	
	public List<PedidoFeedback> porUsuarioEServico(Usuario usuario, Servico servico){
		String jpql = "select p from PedidoFeedback p where p.servico.orcamento.requisicao.usuario = :pUsuario "
				+ "and p.servico = :pServico  and p.situacao = :pSituacao";
		try {
			Query query = em.createQuery(jpql, PedidoFeedback.class);
			query.setParameter("pServico", servico);
			query.setParameter("pUsuario", usuario);
			query.setParameter("pSituacao", Situacao.ATIVO);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
}
