package br.edu.dmsoftware.tcc.modelo;

public enum StatusRequisicao {
	NAO_LIDA("NAO_LIDA", "Não lida"), 
	LIDA("LIDA", "Lida"),
	ENVIADO_ORCAMENTO("ENVIADO_ORCAMENTO", "Enviado Orçamento"),
	FINALIZADO_CONTRATADO("FINALIZADO_CONTRATADO", "Finalizado Contratado"),
	FINALIZADO_RECUSADO_ORCAMENTO("FINALIZADO_RECUSADO_ORCAMENTO", "Finalizado Recusado Orçamento"),
	FINALIZADO_RECUSADO("FINALIZADO_RECUSADO", "Finalizado Recusado");
	
	private String valor;
	private String descricao;
	
	StatusRequisicao(String valor, String descricao){
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
