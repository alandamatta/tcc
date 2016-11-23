package br.edu.dmsoftware.tcc.modelo;

public enum MotivoRejOrcamento {
	NAO_APLICAVEL("NAO_APLICAVEL", "não aplicável"),
	VALOR("VALOR", "Data"),
	DATA("DATA", "Data"),
	DESISTENCIA("DESISTENCIA", "Desistência"),
	OUTRO("OUTRO", "Outro");
	
	private String valor;
	private String descricao;
	
	MotivoRejOrcamento(String valor, String descricao){
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
