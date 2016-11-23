package br.edu.dmsoftware.tcc.modelo;

public enum MotivoRejRequisicao {
	
	NAO_APLICAVEL("NAO_APLICAVEL", "não aplicável"),
	SEM_TEMPO("SEM_TEMPO", "Sem Tempo"),
	NAO_EH_POSSIVEL_EXECUTAR("NAO_EH_POSSIVEL_EXECUTAR", "Não é possível executar"),
	OUTRO("OUTRO", "Outro");
	
	private String valor;
	private String descricao;
	
	MotivoRejRequisicao(String valor, String descricao){
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
