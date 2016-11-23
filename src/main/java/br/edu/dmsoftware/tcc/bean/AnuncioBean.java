package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.AnuncioDao;
import br.edu.dmsoftware.tcc.dao.AvaliacaoAnuncioDao;
import br.edu.dmsoftware.tcc.dao.ComentarioAvaliacaoAnuncioDao;
import br.edu.dmsoftware.tcc.dao.ComentarioDao;
import br.edu.dmsoftware.tcc.dao.ImagemAnuncioDao;
import br.edu.dmsoftware.tcc.dao.RespostaDao;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.AvaliacaoAnuncio;
import br.edu.dmsoftware.tcc.modelo.Comentario;
import br.edu.dmsoftware.tcc.modelo.ComentarioAvaliacaoAnuncio;
import br.edu.dmsoftware.tcc.modelo.ImagemAnuncio;
import br.edu.dmsoftware.tcc.modelo.Recomenda;
import br.edu.dmsoftware.tcc.modelo.Resposta;
import br.edu.dmsoftware.tcc.modelo.Situacao;

@Model
@ViewScoped
public class AnuncioBean implements Serializable{
	
	private Long id;
	@Inject
	private Anuncio anuncio;
	@Inject
	private AnuncioDao anuncioDao;
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	
	@Inject
	private Comentario comentario;
	@Inject
	private ComentarioDao comentarioDao;
	private List<Comentario> comentarios;
	@Inject
	private RespostaDao respostaDao;
	private List<Resposta> respostas;
	@Inject
	private ImagemAnuncioDao imagemAnuncioDao;
	private List<ImagemAnuncio> imagensAnuncio;
	@Inject
	private AvaliacaoAnuncioDao avaliacaoAnuncioDao;
	private List<AvaliacaoAnuncio> avaliacaoAnuncios = new ArrayList<>();
	private List<AvaliacaoAnuncio> avaliacaoPositivaAnuncios = new ArrayList<>();
	private List<AvaliacaoAnuncio> avaliacaoNegativaAnuncios = new ArrayList<>();
	
	private List<ComentarioAvaliacaoAnuncio> comentariosPositivos = new ArrayList<>();
	private List<ComentarioAvaliacaoAnuncio> comentariosNegativos = new ArrayList<>();
	
	@Inject
	private ComentarioAvaliacaoAnuncioDao comentarioAvaliacaoAnuncioDao;
	
	private Integer numeroMedioDeEstrelas = 0;
	
	private Integer numeroTotalDeEstrelas = 0;
	private Integer q1estrela = 0;
	private Integer q2estrela = 0;
	private Integer q3estrela = 0;
	private Integer q4estrela = 0;
	private Integer q5estrela = 0;
	private Integer q0estrela = 0;
	
	private String valueBtnLogin;
	
	private boolean edicao;
	private boolean comentar;
	private boolean responder;
	private boolean disableInteresse;
	
	
	public void carregarAnuncio(){
		if(id != null){
			this.anuncio = anuncioDao.buscaPeloId(id);
			if(this.anuncio != null){
				carregarComentarios();
				carregaFotosDoAnuncio();
				carregarAvaliacoes();
				avaliacaoPorQtdDeEstrela();
				//usuario logado é criador do anúncio, pode somente responder
				if((usuarioLogado.isLogado()) && (usuarioLogado.getUsuario().equals(anuncio.getUsuario()))){
					setEdicao(true);
					setComentar(false);
					setResponder(true);
					setDisableInteresse(true);
					setValueBtnLogin("Sair");
				//tem usuario logado, podendo somente comentar
				}else if((usuarioLogado.isLogado()) && (!this.anuncio.getSituacao().equals(Situacao.PAUSADO))){
					setComentar(true);
					setResponder(false);
					setDisableInteresse(false);
					setValueBtnLogin("Sair");
				//não tem usuario logado, nao pode comentar
				}else{
					setComentar(false);
					setResponder(false);
					setDisableInteresse(true);
					setValueBtnLogin("Entrar");
				}
			}
		}
	}
	
	public void carregarComentariosPositivosENegativos(){
		comentariosPositivos = comentarioAvaliacaoAnuncioDao.NegativoPorAnuncio(this.anuncio);
		comentariosNegativos = comentarioAvaliacaoAnuncioDao.positivoPorAnuncio(this.anuncio);
	}
	
	public void avaliacaoPorQtdDeEstrela(){
		for (AvaliacaoAnuncio avaliacaoAnuncio : avaliacaoAnuncios) {
			System.out.println("XPAS"+avaliacaoAnuncios.size());
			numeroTotalDeEstrelas += avaliacaoAnuncio.getNumeroDeEstrelas();
			if(avaliacaoAnuncio.getNumeroDeEstrelas() == 1){
				q1estrela += 1;
			}else if(avaliacaoAnuncio.getNumeroDeEstrelas() == 2){
				q2estrela += 1;
			}else if(avaliacaoAnuncio.getNumeroDeEstrelas() == 3){
				q3estrela += 1;
			}else if(avaliacaoAnuncio.getNumeroDeEstrelas() == 4){
				q4estrela += 1;
			}else if(avaliacaoAnuncio.getNumeroDeEstrelas() == 5){
				q5estrela += 1;
			}else if(avaliacaoAnuncio.getNumeroDeEstrelas() == 0){
				q0estrela += 1;
			}
			if(avaliacaoAnuncio.getRecomenda().equals(Recomenda.SIM)){
				//recomendacoes
				avaliacaoPositivaAnuncios.add(avaliacaoAnuncio);
			}else if(avaliacaoAnuncio.getRecomenda().equals(Recomenda.NAO)){
				//nao recomendam
				avaliacaoNegativaAnuncios.add(avaliacaoAnuncio);
			}
		}
	}
	
	public void tirarMediaDeEstrelas(){
		numeroMedioDeEstrelas = numeroTotalDeEstrelas / avaliacaoAnuncios.size();
	}
	
	public void carregarAvaliacoes(){
		this.avaliacaoAnuncios = avaliacaoAnuncioDao.todosPorAnuncio(this.anuncio);
	}
	
	
	public void carregaFotosDoAnuncio(){
		this.imagensAnuncio = imagemAnuncioDao.porAnuncio(this.anuncio);
	}
	
	public String chamarRequisicao(){
		return "/contratante/requisicao.jsf?faces-redirect=true&anuncio="+id;
	}
	
	public void editarTitulo(){
		try {
			anuncioDao.salvar(anuncio);
		} catch (Exception e) {
			new Mensagens().erro();
		}
	}
	
	public void carregarComentarios(){
		comentarios = comentarioDao.buscaComentarioPorAnuncio(this.anuncio);
	}
	
	@Transactional
	public void salvarComentario(){
		comentario.setAnuncio(anuncio);
		comentario.setDataComentario(Calendar.getInstance());
		comentario.setUsuario(usuarioLogado.getUsuario());
		comentarioDao.salvar(comentario);
		limparComentario();
	}
	
	public void carregarRespostas(){
		this.respostas = this.respostaDao.porAnuncio(this.anuncio);
	}
	
	public String acaoBtnLogar(){
		if(usuarioLogado.isLogado()){
			usuarioLogado.deslogar();
			return "/anuncio.jsf?faces-redirect=true&id="+id;
		}else{
			return "/login.jsf?faces-redirect=true";
		}
	}
	
	public void limparComentario(){
		this.comentario = new Comentario();
	}
	
	public Anuncio getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public UsuarioLogadoBean getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(UsuarioLogadoBean usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public boolean isEdicao() {
		return edicao;
	}
	public void setEdicao(boolean edicao) {
		this.edicao = edicao;
	}
	
	public Comentario getComentario() {
		return comentario;
	}
	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public boolean isComentar() {
		return comentar;
	}
	public void setComentar(boolean comentar) {
		this.comentar = comentar;
	}
	
	public boolean isResponder() {
		return responder;
	}
	public void setResponder(boolean responder) {
		this.responder = responder;
	}
	
	public String getValueBtnLogin() {
		return valueBtnLogin;
	}
	public void setValueBtnLogin(String valueBtnLogin) {
		this.valueBtnLogin = valueBtnLogin;
	}
	
	public boolean isDisableInteresse() {
		return disableInteresse;
	}
	public void setDisableInteresse(boolean disableInteresse) {
		this.disableInteresse = disableInteresse;
	}
	
	public List<ImagemAnuncio> getImagensAnuncio() {
		return imagensAnuncio;
	}
	public void setImagensAnuncio(List<ImagemAnuncio> imagensAnuncio) {
		this.imagensAnuncio = imagensAnuncio;
	}

	public Integer getNumeroTotalDeEstrelas() {
		return numeroTotalDeEstrelas;
	}

	public void setNumeroTotalDeEstrelas(Integer numeroTotalDeEstrelas) {
		this.numeroTotalDeEstrelas = numeroTotalDeEstrelas;
	}

	public Integer getNumeroMedioDeEstrelas() {
		return numeroMedioDeEstrelas;
	}

	public void setNumeroMedioDeEstrelas(Integer numeroMedioDeEstrelas) {
		this.numeroMedioDeEstrelas = numeroMedioDeEstrelas;
	}

	public Integer getQ1estrela() {
		return q1estrela;
	}

	public void setQ1estrela(Integer q1estrela) {
		this.q1estrela = q1estrela;
	}

	public Integer getQ2estrela() {
		return q2estrela;
	}

	public void setQ2estrela(Integer q2estrela) {
		this.q2estrela = q2estrela;
	}

	public Integer getQ3estrela() {
		return q3estrela;
	}

	public void setQ3estrela(Integer q3estrela) {
		this.q3estrela = q3estrela;
	}

	public Integer getQ4estrela() {
		return q4estrela;
	}

	public void setQ4estrela(Integer q4estrela) {
		this.q4estrela = q4estrela;
	}

	public Integer getQ5estrela() {
		return q5estrela;
	}

	public void setQ5estrela(Integer q5estrela) {
		this.q5estrela = q5estrela;
	}

	public Integer getQ0estrela() {
		return q0estrela;
	}

	public void setQ0estrela(Integer q0estrela) {
		this.q0estrela = q0estrela;
	}

	public List<AvaliacaoAnuncio> getAvaliacaoAnuncios() {
		return avaliacaoAnuncios;
	}

	public void setAvaliacaoAnuncios(List<AvaliacaoAnuncio> avaliacaoAnuncios) {
		this.avaliacaoAnuncios = avaliacaoAnuncios;
	}

	public List<AvaliacaoAnuncio> getAvaliacaoPositivaAnuncios() {
		return avaliacaoPositivaAnuncios;
	}

	public void setAvaliacaoPositivaAnuncios(List<AvaliacaoAnuncio> avaliacaoPositivaAnuncios) {
		this.avaliacaoPositivaAnuncios = avaliacaoPositivaAnuncios;
	}

	public List<AvaliacaoAnuncio> getAvaliacaoNegativaAnuncios() {
		return avaliacaoNegativaAnuncios;
	}

	public void setAvaliacaoNegativaAnuncios(List<AvaliacaoAnuncio> avaliacaoNegativaAnuncios) {
		this.avaliacaoNegativaAnuncios = avaliacaoNegativaAnuncios;
	}

	public List<ComentarioAvaliacaoAnuncio> getComentariosPositivos() {
		return comentariosPositivos;
	}

	public void setComentariosPositivos(List<ComentarioAvaliacaoAnuncio> comentariosPositivos) {
		this.comentariosPositivos = comentariosPositivos;
	}

	public List<ComentarioAvaliacaoAnuncio> getComentariosNegativos() {
		return comentariosNegativos;
	}

	public void setComentariosNegativos(List<ComentarioAvaliacaoAnuncio> comentariosNegativos) {
		this.comentariosNegativos = comentariosNegativos;
	}
	
	
	
}
