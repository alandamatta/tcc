package br.edu.dmsoftware.tcc.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.websocket.Decoder.Text;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Anuncio implements Serializable{

	private Long id;
	private Usuario usuario;
	private CategoriaNv1 categoria;
	private String titulo;
	private Integer reputacao;
	private List<ImagemAnuncio> imagens;
	private List<Comentario> comentarios;
	private Situacao situacao;
	private Entrada entrada;
	private Parcela parcela;
	private Cidade cidade;
	private boolean boleto;
	private boolean cartaoCredito;
	private boolean cartaoDebito;
	private boolean dinheiro;
	private String telefone;
	private String celular;
	private String descricaoAnuncio;
	private Integer parcelamentoMax = 0;//valor default caso nao preenchido
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne
	@NotNull
	public CategoriaNv1 getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaNv1 categoria) {
		this.categoria = categoria;
	}
	
	@Column(nullable = false)
	public Integer getReputacao() {
		return reputacao;
	}
	
	public void setReputacao(Integer reputacao) {
		this.reputacao = reputacao;
	}

	@OneToMany(mappedBy = "anuncio", fetch=FetchType.EAGER)
	
	public List<ImagemAnuncio> getImagens() {
		return imagens;
	}

	public void setImagens(List<ImagemAnuncio> imagens) {
		this.imagens = imagens;
	}
	
	@OneToMany(mappedBy = "anuncio")
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	@Enumerated(EnumType.STRING)
	public Situacao getSituacao() {
		return situacao;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	@NotNull
	@NotBlank
	@Length(min=8, max=40)
	@Column(length=40)
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Enumerated(EnumType.STRING)
	public Entrada getEntrada() {
		return entrada;
	}
	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}
	
	@Enumerated(EnumType.STRING)
	public Parcela getParcela() {
		return parcela;
	}
	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}
	
	public boolean isBoleto() {
		return boleto;
	}
	public void setBoleto(boolean boleto) {
		this.boleto = boleto;
	}

	public boolean isCartaoCredito() {
		return cartaoCredito;
	}
	public void setCartaoCredito(boolean cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}

	public boolean isCartaoDebito() {
		return cartaoDebito;
	}
	public void setCartaoDebito(boolean cartaoDebito) {
		this.cartaoDebito = cartaoDebito;
	}

	public boolean isDinheiro() {
		return dinheiro;
	}
	public void setDinheiro(boolean dinheiro) {
		this.dinheiro = dinheiro;
	}
	
	@ManyToOne
	@NotNull(message="Cidade: É obrigatório")
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	@NotNull
	@NotBlank
	@Column
	@Lob
	public String getDescricaoAnuncio() {
		return descricaoAnuncio;
	}
	public void setDescricaoAnuncio(String descricaoAnuncio) {
		this.descricaoAnuncio = descricaoAnuncio;
	}
	
	public Integer getParcelamentoMax() {
		return parcelamentoMax;
	}
	public void setParcelamentoMax(Integer parcelamentoMax) {
		this.parcelamentoMax = parcelamentoMax;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anuncio other = (Anuncio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	}	
	
}
