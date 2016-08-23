package br.edu.dmsoftware.tcc.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.modelo.Usuario;

public class UsuarioDao extends GenericDaoImp<Usuario, Long>{

	@PersistenceContext
	private EntityManager em;
	
	public boolean emailExiste(String email){
		String jpql = "select u from Usuario u where u.email = :pEmail";
		Query query = em.createQuery(jpql, Usuario.class);
		query.setParameter("pEmail", email);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}		
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
	
	public Usuario buscaPorUsuario(String usuario){
		Usuario user = new Usuario();
		if(usuarioExiste(usuario)){
			String jpql = "select u from Usuario u where u.usuario = :pUsuario";
			Query query = em.createQuery(jpql, Usuario.class);
			query.setParameter("pUsuario", usuario);
			user = (Usuario) query.getSingleResult();
			return user;
		}else{
			return null;
		}
	}
}
