package br.edu.dmsoftware.tcc.modelo;

public enum TipoDeDado {
	NUMEROS_E_LETRAS("Números e Letras", "NUMEROS_E_LETRAS", "/[a-z0-9_]/i"),
	NUMERO("Somente Números", "NUMEROS", "/[\\d\\-\\.]/"),
	LETRAS("Somente Letras", "LETRAS", "/[a-z_]/i");
	
	private String descricao;
	private String valor;
	private String keyFilter;
	
	TipoDeDado(String descricao, String valor, String keyFilter){
		this.descricao = descricao;
		this.valor = valor;
		this.keyFilter = keyFilter;
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
	
	public String getKeyFilter() {
		return keyFilter;
	}
	public void setKeyFilter(String keyFilter) {
		this.keyFilter = keyFilter;
	}
}
