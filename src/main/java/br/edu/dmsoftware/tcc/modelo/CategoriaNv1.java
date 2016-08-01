package br.edu.dmsoftware.tcc.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class CategoriaNv1 {

	private Long id;
	private String descricao;
	private CategoriaNv0 categoriaPai;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(length=50)
	@NotNull
	@NotBlank
	@Length(min=4, max=50)
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@ManyToOne
	@NotNull
	public CategoriaNv0 getCategoriaPai() {
		return categoriaPai;
	}
	
	public void setCategoriaPai(CategoriaNv0 categoriaPai) {
		this.categoriaPai = categoriaPai;
	}
	
	
		
}
