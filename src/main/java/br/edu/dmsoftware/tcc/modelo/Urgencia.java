package br.edu.dmsoftware.tcc.modelo;

public enum Urgencia {
	BAIXA("Baixa", "BAIXA"), 
	MEDIA("Média", "MEDIA"), 
	ALTA("Alta", "ALTA");
	
	private String descricao;
	private String valor;
	
	Urgencia(String descricao, String valor) {
		this.descricao = descricao;
		this.valor = valor;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
}
