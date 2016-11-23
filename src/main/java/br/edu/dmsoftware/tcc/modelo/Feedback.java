package br.edu.dmsoftware.tcc.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Feedback implements Serializable{
	
	private Long id;
	private PedidoFeedback pedidoFeedback; 
	private String imagem;
	private String mensagem;
	private Calendar dataDoFeedback;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne
	public PedidoFeedback getPedidoFeedback() {
		return this.pedidoFeedback;
	}
	public void setPedidoFeedback(PedidoFeedback pedidoFeedBack) {
		this.pedidoFeedback = pedidoFeedBack;
	}
	
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public Calendar getDataDoFeedback() {
		return dataDoFeedback;
	}
	public void setDataDoFeedback(Calendar dataDoFeedback) {
		this.dataDoFeedback = dataDoFeedback;
	}
}