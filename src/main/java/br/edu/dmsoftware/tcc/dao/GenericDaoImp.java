package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.edu.dmsoftware.tcc.modelo.Usuario;

public class GenericDaoImp<T, ID extends Serializable> implements GenericDao<T, ID>, Serializable{
	

	private Class<T> classeEntidade;	
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public GenericDaoImp() {
		this.classeEntidade = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public T buscaPeloId(ID id) {
		Long cod = (Long) id;
		return em.find(classeEntidade, cod);
	}

	@Override
	public T salvar(T entidade) {
		T entidadeRetorno = em.merge(entidade);
		return (T) entidadeRetorno;
	}

	@Override
	public void remover(T entidade) {
		em.remove(em.merge(entidade));
	}

	@Override
	public List<T> buscarTodos() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = criteriaBuilder.createQuery(classeEntidade);
		query.from(classeEntidade);
		TypedQuery<T> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();
	}
	
	public boolean usuarioExiste(String usuario){
		String jpql = "select u from Usuario u where u.usuario = :pUsuario";
		Query query = em.createQuery(jpql, Usuario.class);
		query.setParameter("pUsuario", usuario);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}		
	}
}
