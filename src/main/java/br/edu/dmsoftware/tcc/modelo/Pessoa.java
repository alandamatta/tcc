package br.edu.dmsoftware.tcc.modelo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pessoa {
	
	private Long id;
	private String nome;
	private String endereco;
	private Integer numEndereco;
	private String complemento;
	private Integer cep;
	private Cidade cidade;
	private String numero1;
	private String numero2;
	private Integer dadosCompletos; //EX.: 80% dos dados completos
	private Calendar dataCadastro;
	
	private Usuario usuario;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length=40)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(length=50)
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@Column(length=8)
	public Integer getNumEndereco() {
		return numEndereco;
	}
	public void setNumEndereco(Integer numEndereco) {
		this.numEndereco = numEndereco;
	}
	
	@Column(length=20)
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	@Column(length=9)
	public Integer getCep() {
		return cep;
	}
	public void setCep(Integer cep) {
		this.cep = cep;
	}
	
	@ManyToOne
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@Column(length=10)
	public String getNumero1() {
		return numero1;
	}
	public void setNumero1(String numero1) {
		this.numero1 = numero1;
	}
	
	@Column(length=10)
	public String getNumero2() {
		return numero2;
	}
	public void setNumero2(String numero2) {
		this.numero2 = numero2;
	}
	
	@Column(length=3)
	public Integer getDadosCompletos() {
		return dadosCompletos;
	}
	public void setDadosCompletos(Integer dadosCompletos) {
		this.dadosCompletos = dadosCompletos;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@OneToOne(optional=true)
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
