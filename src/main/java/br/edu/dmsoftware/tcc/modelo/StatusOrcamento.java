package br.edu.dmsoftware.tcc.modelo;

public enum StatusOrcamento {
	NAO_LIDA("NAO_LIDA", "Não lida"), 
	LIDA("LIDA", "Lida"),
	NAO_ACEITA("NAO_ACEITA", "Não Aceita"),
	CONTRATADO("CONTRATADO", "Contratado"), 
	FINALIZADO("FINALIZADO", "Finalizado");
	
	private String valor;
	private String descricao;
	
	StatusOrcamento(String valor, String descricao){
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
