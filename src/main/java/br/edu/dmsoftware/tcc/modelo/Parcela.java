package br.edu.dmsoftware.tcc.modelo;

public enum Parcela {
	SIM("Sim", "SIM"), 
	NAO("Não", "NAO"), 
	A_COMBINAR("À Combinar", "A_COMBINAR");
	
	String descricao;
	String valor;
	
	Parcela(String descricao, String valor){
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
