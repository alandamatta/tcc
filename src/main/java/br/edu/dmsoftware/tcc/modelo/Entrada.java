package br.edu.dmsoftware.tcc.modelo;

public enum Entrada {
	SIM("Sim", "SIM"),
	NAO("Não", "NAO"),
	COMBINAR("À Combinar", "A_COMBINAR");
	
	String descricao;
	String valor;
	
	Entrada(String descricao, String valor){
		this.valor = valor;
		this.descricao = descricao;
	}
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
