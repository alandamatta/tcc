package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.AnuncioDao;
import br.edu.dmsoftware.tcc.dao.AvaliacaoAnuncioDao;

@Model
public class TestaView implements Serializable{
	@Inject
	private AvaliacaoAnuncioDao avaliacaoAnuncioDao;
	@Inject
	private AnuncioDao anuncioDao;
	private Integer numeroDeEstrelas = (Integer) avaliacaoAnuncioDao.totalEstrelasPorAnuncio(anuncioDao.buscaPeloId(1L));
	
	@PostConstruct
	public void init(){
		
	}
	
	public Integer getNumeroDeEstrelas() {
		return numeroDeEstrelas;
	}
	
}
