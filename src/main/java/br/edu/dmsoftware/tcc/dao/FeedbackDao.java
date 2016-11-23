package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Comentario;
import br.edu.dmsoftware.tcc.modelo.Feedback;
import br.edu.dmsoftware.tcc.modelo.PedidoFeedback;

public class FeedbackDao extends GenericDaoImp<Feedback, Long> implements Serializable{
	
}
