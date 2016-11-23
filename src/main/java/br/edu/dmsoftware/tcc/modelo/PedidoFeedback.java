package br.edu.dmsoftware.tcc.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class PedidoFeedback implements Serializable{
	
	private Long id;
	private Servico servico;
	private boolean imagemObrigatoria;
	private String mensagem;
	private Situacao situacao;
	private Feedback feedback;
	private boolean visualizada;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	public boolean isImagemObrigatoria() {
		return imagemObrigatoria;
	}
	public void setImagemObrigatoria(boolean imagemObrigatoria) {
		this.imagemObrigatoria = imagemObrigatoria;
	}
	
	@Lob
	@NotNull
	@NotBlank
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	@Enumerated(EnumType.STRING)
	public Situacao getSituacao() {
		return situacao;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	@OneToOne(mappedBy="pedidoFeedback")
	public Feedback getFeedback() {
		return feedback;
	}
	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
	
	public boolean isVisualizada() {
		return visualizada;
	}
	public void setVisualizada(boolean visualizada) {
		this.visualizada = visualizada;
	}
	
	
}
