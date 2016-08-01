package br.edu.dmsoftware.tcc.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {
	
	public T buscaPeloId(ID Long);	
	public void salvar(T entidade);
	public void remover(T entidade);
	public List buscarTodos();
	
}
