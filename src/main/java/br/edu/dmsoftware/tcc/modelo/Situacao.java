package br.edu.dmsoftware.tcc.modelo;

public enum Situacao {
	ATIVO("Ativo", "ATIVO", "Pausar"),
	INATIVO("Inativo", "INATIVO", "SemAcao"),
	PAUSADO("Pausado", "PAUSADO", "Ativar");
	
	String descricao;
	String valor;
	String acao;
	
	Situacao(String descricao, String valor, String acao){
		this.valor = valor;
		this.descricao = descricao;
		this.acao = acao;
		
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
	
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
}
