
package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.edu.dmsoftware.tcc.dao.AnuncioDao;
import br.edu.dmsoftware.tcc.dao.CidadeDao;
import br.edu.dmsoftware.tcc.dao.EstadoDao;
import br.edu.dmsoftware.tcc.dao.PerguntaDao;
import br.edu.dmsoftware.tcc.dao.RequisicaoDao;
import br.edu.dmsoftware.tcc.dao.ResPerAnuncioDao;
import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Cidade;
import br.edu.dmsoftware.tcc.modelo.Estado;
import br.edu.dmsoftware.tcc.modelo.Pergunta;
import br.edu.dmsoftware.tcc.modelo.Requisicao;
import br.edu.dmsoftware.tcc.modelo.ResPerAnuncio;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.StatusRequisicao;
import br.edu.dmsoftware.tcc.modelo.Urgencia;

@Model
@ViewScoped
public class RequisicaoBean implements Serializable{
	
	private Long id;
	@Inject
	private Requisicao requisicao;
	@Inject
	private RequisicaoDao requisicaoDao;
	@Inject
	private Anuncio anuncio;
	@Inject
	private AnuncioDao anuncioDao;
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private Estado estado;
	@Inject
	private EstadoDao estadoDao;
	private List<Estado> estados;
	@Inject
	private Cidade cidade;
	@Inject
	private CidadeDao cidadeDao;
	private List<Cidade> cidades;
	@Inject
	private Pergunta pergunta;
	@Inject
	private PerguntaDao perguntaDao;
	private List<Pergunta> perguntas;
	@Inject
	private Pergunta pergunta1;
	@Inject
	private Pergunta pergunta2;
	@Inject
	private Pergunta pergunta3;
	@Inject
	private Pergunta pergunta4;
	@Inject
	private Pergunta pergunta5;
	@Inject
	private ResPerAnuncio respostaDaPergunta1;
	@Inject
	private ResPerAnuncio respostaDaPergunta2;
	@Inject
	private ResPerAnuncio respostaDaPergunta3;
	@Inject
	private ResPerAnuncio respostaDaPergunta4;
	@Inject
	private ResPerAnuncio respostaDaPergunta5;
	@Inject
	private ResPerAnuncioDao resPerAnuncioDao;
	private boolean disableCidade = true;
	private boolean disableEndereco = true;
	
	private boolean pergunta1Rendered = false;
	private boolean pergunta2Rendered = false;
	private boolean pergunta3Rendered = false;
	private boolean pergunta4Rendered = false;
	private boolean pergunta5Rendered = false;
	
	private String centroMapa = "41.850033, -87.6500523";
	
	private MapModel modeloMapa;
	
	private boolean mostraRequisicao;
	private boolean msgPaginaInexistente;
	private String dataAtual;
	private String dataFimMinima;
	
	private boolean dataFimDisbale = true;
	
	@PostConstruct
	public void init(){
		carregarEstados();
		modeloMapa = new DefaultMapModel();
		pegarData();
	}
	
	//ao abrir a view
	public void carregarAnuncio(){
		buscarAnuncio();
		carregarPerguntas();
		Integer qtdPerguntas = perguntas.size();
		//quais pergunta serão renderizados
		switch (qtdPerguntas) {
		case 1:
			setPergunta1Rendered(true);
			break;
		case 2:
			setPergunta1Rendered(true);
			setPergunta2Rendered(true);
			break;
		case 3:
			setPergunta1Rendered(true);
			setPergunta2Rendered(true);
			setPergunta3Rendered(true);
			break;
		case 4:
			setPergunta1Rendered(true);
			setPergunta2Rendered(true);
			setPergunta3Rendered(true);
			setPergunta4Rendered(true);
			break;
		case 5:
			setPergunta1Rendered(true);
			setPergunta2Rendered(true);
			setPergunta3Rendered(true);
			setPergunta4Rendered(true);
			setPergunta5Rendered(true);
			break;
		}
	}
	
	@Transactional
	public void salvar(){
		this.requisicao.setAnuncio(this.anuncio);
		this.requisicao.setData(Calendar.getInstance());
		this.requisicao.setSituacao(Situacao.ATIVO);
		this.requisicao.setStatus(StatusRequisicao.NAO_LIDA);
		this.requisicao.setData(Calendar.getInstance());
		this.requisicao.setUsuario(usuarioLogado.getUsuario());
		this.requisicao.setNotificado(false);
		Requisicao requisicaoTemp = requisicaoDao.salvar(this.requisicao);
		if((respostaDaPergunta1.getResposta() != null)){
			respostaDaPergunta1.setRequisicao(requisicaoTemp);
			respostaDaPergunta1.setPergunta(pergunta1);
			resPerAnuncioDao.salvar(respostaDaPergunta1);
		}
		
		if(respostaDaPergunta2.getResposta() != null){
			respostaDaPergunta2.setRequisicao(requisicaoTemp);
			respostaDaPergunta2.setPergunta(pergunta2);
			resPerAnuncioDao.salvar(respostaDaPergunta2);
		}
		
		if(respostaDaPergunta3.getResposta() != null){
			respostaDaPergunta3.setRequisicao(requisicaoTemp);
			respostaDaPergunta3.setPergunta(pergunta3);
			resPerAnuncioDao.salvar(respostaDaPergunta3);
		}
		
		if(respostaDaPergunta4.getResposta() != null){
			respostaDaPergunta4.setRequisicao(requisicaoTemp);
			respostaDaPergunta4.setPergunta(pergunta4);
			resPerAnuncioDao.salvar(respostaDaPergunta4);
		}
		
		if(respostaDaPergunta5.getResposta() != null){
			respostaDaPergunta5.setRequisicao(requisicaoTemp);
			respostaDaPergunta5.setPergunta(pergunta5);
			resPerAnuncioDao.salvar(respostaDaPergunta4);
		}
	}
	
	public void onGeoCode(GeocodeEvent event){
		List<GeocodeResult> results = event.getResults();
		if(results != null && !results.isEmpty()){
			LatLng centro = results.get(0).getLatLng();
			centroMapa = centro.getLat() + "," + centro.getLng();
			for(int i = 0;i < results.size();i++){
				GeocodeResult resultado = results.get(i);
				modeloMapa.addOverlay(new Marker(resultado.getLatLng(), resultado.getAddress()));
			}
		}
	}
	
	public void buscarAnuncio(){
		this.anuncio = this.anuncioDao.buscaPeloId(id);
	}
	
	public void carregarEstados(){
		this.estados = estadoDao.buscarTodos();
	}
	
	public void carregarCidades(){
		if(this.estado != null){
			cidades = cidadeDao.buscaCidadePorEstado(this.estado);
			setDisableCidade(false);
		}else{
			setDisableCidade(true);
		}
	}
	
	public void carregarPerguntas(){
		this.perguntas = perguntaDao.perguntasDoAnuncio(this.anuncio);
		for (Pergunta pergunta : this.perguntas) {
			if(pergunta1.getId() == null){
				this.pergunta1 = pergunta;
			}else if(pergunta2.getId() == null){
				this.pergunta2 = pergunta;
			}else if(pergunta3.getId() == null){
				this.pergunta3 = pergunta;
			}else if(pergunta4.getId() == null){
				this.pergunta4 = pergunta;
			}else if(pergunta5.getId() == null){
				this.pergunta5 = pergunta;
			}
		}
	}
	
	public void cidadeAjax(){
		if(this.estado != null && this.cidade != null){
			setDisableEndereco(false);
		}else{
			setDisableEndereco(true);
		}
	}
	
	public void dataInicioAjax(){
		if(this.requisicao.getDataInicio() != null){
			setDataFimDisbale(false);
			setDataFimMinima(this.getRequisicao().getDataInicio().toString());
		}
	}
	
	private void pegarData(){
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yy");
		Date data = new Date();
		dataAtual = simpleDate.format(data);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Requisicao getRequisicao() {
		return requisicao;
	}
	public void setRequisicao(Requisicao requisicao) {
		this.requisicao = requisicao;
	}
	
	public Anuncio getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	
	public UsuarioLogadoBean getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(UsuarioLogadoBean usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	public String getCentroMapa() {
		return centroMapa;
	}
	public void setCentroMapa(String centroMapa) {
		this.centroMapa = centroMapa;
	}
	
	public MapModel getModeloMapa() {
		return modeloMapa;
	}
	public void setModeloMapa(MapModel modeloMapa) {
		this.modeloMapa = modeloMapa;
	}
	
	public boolean isDisableCidade() {
		return disableCidade;
	}
	public void setDisableCidade(boolean disableCidade) {
		this.disableCidade = disableCidade;
	}
	
	public boolean isDisableEndereco() {
		return disableEndereco;
	}
	public void setDisableEndereco(boolean disableEndereco) {
		this.disableEndereco = disableEndereco;
	}
	
	public boolean isMostraRequisicao() {
		return mostraRequisicao;
	}
	public void setMostraRequisicao(boolean mostraRequisicao) {
		this.mostraRequisicao = mostraRequisicao;
	}
	
	public boolean isMsgPaginaInexistente() {
		return msgPaginaInexistente;
	}
	public void setMsgPaginaInexistente(boolean msgPaginaInexistente) {
		this.msgPaginaInexistente = msgPaginaInexistente;
	}
	
	public Pergunta getPergunta() {
		return pergunta;
	}
	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}
	
	public List<Pergunta> getPerguntas() {
		return perguntas;
	}
	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}
	
	public ResPerAnuncio getRespostaDaPergunta1() {
		return respostaDaPergunta1;
	}

	public void setRespostaDaPergunta1(ResPerAnuncio respostaDaPergunta1) {
		this.respostaDaPergunta1 = respostaDaPergunta1;
	}

	public ResPerAnuncio getRespostaDaPergunta2() {
		return respostaDaPergunta2;
	}

	public void setRespostaDaPergunta2(ResPerAnuncio respostaDaPergunta2) {
		this.respostaDaPergunta2 = respostaDaPergunta2;
	}

	public ResPerAnuncio getRespostaDaPergunta3() {
		return respostaDaPergunta3;
	}

	public void setRespostaDaPergunta3(ResPerAnuncio respostaDaPergunta3) {
		this.respostaDaPergunta3 = respostaDaPergunta3;
	}

	public ResPerAnuncio getRespostaDaPergunta4() {
		return respostaDaPergunta4;
	}

	public void setRespostaDaPergunta4(ResPerAnuncio respostaDaPergunta4) {
		this.respostaDaPergunta4 = respostaDaPergunta4;
	}

	public ResPerAnuncio getRespostaDaPergunta5() {
		return respostaDaPergunta5;
	}

	public void setRespostaDaPergunta5(ResPerAnuncio respostaDaPergunta5) {
		this.respostaDaPergunta5 = respostaDaPergunta5;
	}

	public boolean isPergunta1Rendered() {
		return pergunta1Rendered;
	}

	public void setPergunta1Rendered(boolean pergunta1Rendered) {
		this.pergunta1Rendered = pergunta1Rendered;
	}

	public boolean isPergunta2Rendered() {
		return pergunta2Rendered;
	}

	public void setPergunta2Rendered(boolean pergunta2Rendered) {
		this.pergunta2Rendered = pergunta2Rendered;
	}

	public boolean isPergunta3Rendered() {
		return pergunta3Rendered;
	}

	public void setPergunta3Rendered(boolean pergunta3Rendered) {
		this.pergunta3Rendered = pergunta3Rendered;
	}

	public boolean isPergunta4Rendered() {
		return pergunta4Rendered;
	}

	public void setPergunta4Rendered(boolean pergunta4Rendered) {
		this.pergunta4Rendered = pergunta4Rendered;
	}

	public boolean isPergunta5Rendered() {
		return pergunta5Rendered;
	}

	public void setPergunta5Rendered(boolean pergunta5Rendered) {
		this.pergunta5Rendered = pergunta5Rendered;
	}

	public Pergunta getPergunta1() {
		return pergunta1;
	}

	public void setPergunta1(Pergunta pergunta1) {
		this.pergunta1 = pergunta1;
	}

	public Pergunta getPergunta2() {
		return pergunta2;
	}

	public void setPergunta2(Pergunta pergunta2) {
		this.pergunta2 = pergunta2;
	}

	public Pergunta getPergunta3() {
		return pergunta3;
	}

	public void setPergunta3(Pergunta pergunta3) {
		this.pergunta3 = pergunta3;
	}

	public Pergunta getPergunta4() {
		return pergunta4;
	}

	public void setPergunta4(Pergunta pergunta4) {
		this.pergunta4 = pergunta4;
	}

	public Pergunta getPergunta5() {
		return pergunta5;
	}

	public void setPergunta5(Pergunta pergunta5) {
		this.pergunta5 = pergunta5;
	}
	
	public Urgencia[] getUrgencia(){
		return Urgencia.values();
	}
	
	public String getDataAtual() {
		return dataAtual;
	}
	public void setDataAtual(String dataAtual) {
		this.dataAtual = dataAtual;
	}
	
	public String getDataFimMinima() {
		return dataFimMinima;
	}
	public void setDataFimMinima(String dataFimMinima) {
		this.dataFimMinima = dataFimMinima;
	}
	
	public boolean isDataFimDisbale() {
		return dataFimDisbale;
	}
	public void setDataFimDisbale(boolean dataFimDisbale) {
		this.dataFimDisbale = dataFimDisbale;
	}
	
}
