package br.edu.dmsoftware.tcc.modelo;

public enum Recomenda {
	SIM("SIM", "Sim"), 
	NAO("NAO", "Não");
	
	private String valor;
	private String descricao;
	
	Recomenda(String valor, String descricao){
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
