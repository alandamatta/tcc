package br.edu.dmsoftware.tcc.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.edu.dmsoftware.tcc.dao.AnuncioDao;
import br.edu.dmsoftware.tcc.dao.CategoriaNv0Dao;
import br.edu.dmsoftware.tcc.dao.CategoriaNv1Dao;
import br.edu.dmsoftware.tcc.dao.CidadeDao;
import br.edu.dmsoftware.tcc.dao.EstadoDao;
import br.edu.dmsoftware.tcc.dao.ImagemAnuncioDao;
import br.edu.dmsoftware.tcc.dao.PerguntaDao;
import br.edu.dmsoftware.tcc.dao.PessoaDao;
import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.MD5Crypt;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.CategoriaNv0;
import br.edu.dmsoftware.tcc.modelo.CategoriaNv1;
import br.edu.dmsoftware.tcc.modelo.Cidade;
import br.edu.dmsoftware.tcc.modelo.Entrada;
import br.edu.dmsoftware.tcc.modelo.Estado;
import br.edu.dmsoftware.tcc.modelo.ImagemAnuncio;
import br.edu.dmsoftware.tcc.modelo.Nivel;
import br.edu.dmsoftware.tcc.modelo.Parcela;
import br.edu.dmsoftware.tcc.modelo.Pergunta;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.TipoDeDado;
import br.edu.dmsoftware.tcc.modelo.Usuario;

@Model
@ViewScoped
public class NovoAnuncioBean implements Serializable{
	@Inject
	private Anuncio anuncio;
	@Inject
	private AnuncioDao anuncioDao;
	@Inject
	private Usuario usuario;
	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	private  List<CategoriaNv0> categoriasNv0;
	@Inject
	private CategoriaNv0Dao categoriaNv0Dao;
	@Inject
	private CategoriaNv0 categoriaNv0;
	
	private List<CategoriaNv1> categoriasNv1;
	@Inject
	private CategoriaNv1Dao categoriaNv1Dao;
	@Inject
	private CategoriaNv1 categoriaNv1;
	
	@Inject
	private PessoaDao pessoaDao;
	
	@Inject
	private CidadeDao cidadeDao;
	private List<Cidade> cidades;
	private Cidade cidade;
	
	@Inject
	private EstadoDao estadoDao;
	private List<Estado> estados;
	@Inject
	private Estado estado;
	@Inject
	private Pergunta pergunta1;
	private boolean pergunta1Disable = true;
	@Inject
	private Pergunta pergunta2;
	private boolean pergunta2Disable = true;
	@Inject
	private Pergunta pergunta3;
	private boolean pergunta3Disable = true;;
	@Inject
	private Pergunta pergunta4;
	private boolean pergunta4Disable = true;;
	@Inject
	private Pergunta pergunta5;
	private boolean pergunta5Disable = true;;
	@Inject
	private PerguntaDao perguntaDao;
	
	@Inject
	private ImagemAnuncio imagem;
	@Inject
	private ImagemAnuncio imagem2;
	@Inject
	private ImagemAnuncio imagem3;
	@Inject
	private ImagemAnuncio imagem4;
	@Inject
	private ImagemAnuncioDao imagemDao;
	
	private String diretorioTemp;
	private String diretorioTemp2;
	private String diretorioTemp3;
	private String diretorioTemp4;
	
	private boolean mostraImagem1 = false;
	private boolean mostraImagem2 = false;
	private boolean mostraImagem3 = false;
	private boolean mostraImagem4 = false;
	private boolean mostraUpload2 = false;
	private boolean mostraUpload3 = false;
	private boolean mostraUpload4 = false;
	private boolean renderizaMsg = true;
	private boolean disableComboBox = true;
	private boolean renderizaParcelamentoMax = false;
	private boolean renderizaWizard = false;
	
	@PostConstruct
    public void init() {
		if(!pessoaDao.pessoaExiste(usuarioLogado.getUsuario())){
			setRenderizaWizard(false);
		}else{
			setRenderizaWizard(true);
			carregaCategoriasNv0();
			carregaEstados();
		}
		
	}
	
	public void parcelamentoAjax(){
		if(this.anuncio.getParcela().equals(Parcela.SIM)){
			setRenderizaParcelamentoMax(true);
		}else{
			setRenderizaParcelamentoMax(false);
		}
	}
	
	@Transactional
	public String criarAnuncio(){
		
		try {
			if(usuarioLogado.getUsuario().getNivel().equals(Nivel.CONTRATANTE)){
				usuarioLogado.getUsuario().setNivel(Nivel.ANUNCIANTE);
				usuarioLogado.getUsuario().setDataPrimeiroAnuncio(Calendar.getInstance());
				
				usuarioDao.salvar(usuarioLogado.getUsuario());
			}
			anuncio.setUsuario(usuarioLogado.getUsuario());
			anuncio.setReputacao(0);
			anuncio.setSituacao(Situacao.ATIVO);
			Anuncio anuncioTemp = anuncioDao.salvar(anuncio);
			
			if(diretorioTemp != null){
				imagem.setAnuncio(anuncioTemp);
				imagem.setDataUpload(Calendar.getInstance());
				imagemDao.salvar(imagem);
			}
			
			if(diretorioTemp2 != null){
				imagem2.setAnuncio(anuncioTemp);
				imagem2.setDataUpload(Calendar.getInstance());
				imagemDao.salvar(imagem2);
			}
			
			if(diretorioTemp3 != null){
				imagem3.setAnuncio(anuncioTemp);
				imagem3.setDataUpload(Calendar.getInstance());
				imagemDao.salvar(imagem3);
			}
			
			if(diretorioTemp4 != null){
				imagem4.setAnuncio(anuncioTemp);
				imagem4.setDataUpload(Calendar.getInstance());
				imagemDao.salvar(imagem4);
			}
			
			if(!pergunta1Disable){
				pergunta1.setAnuncio(anuncioTemp);
				perguntaDao.salvar(pergunta1);
			}
			
			if(!pergunta2Disable){
				pergunta2.setAnuncio(anuncioTemp);
				perguntaDao.salvar(pergunta2);
			}
			
			if(!pergunta3Disable){
				pergunta3.setAnuncio(anuncioTemp);
				perguntaDao.salvar(pergunta3);
			}
			
			if(!pergunta4Disable){
				pergunta4.setAnuncio(anuncioTemp);
				perguntaDao.salvar(pergunta4);
			}
			
			if(!pergunta5Disable){
				pergunta5.setAnuncio(anuncioTemp);
				perguntaDao.salvar(pergunta5);
			}
			limpar();
			return "/anunciante/novoAnuncio.jsf?faces-redirect=true";
		} catch (RuntimeException e) {
			new Mensagens().falhaAoSalvar();
			return "";
		}
		
		
	}
	
	public void pergunta1OnClick(){
		if(isPergunta1Disable()){
			setPergunta1Disable(false);
		}else{
			setPergunta1Disable(true);
		}
	}
	
	public void pergunta2OnClick(){
		if(isPergunta2Disable()){
			setPergunta2Disable(false);
		}else{
			setPergunta2Disable(true);
		}
	}
	
	public void pergunta3OnClick(){
		if(isPergunta3Disable()){
			setPergunta3Disable(false);
		}else{
			setPergunta3Disable(true);
		}
	}
	
	public void pergunta4OnClick(){
		if(isPergunta4Disable()){
			setPergunta4Disable(false);
		}else{
			setPergunta4Disable(true);
		}
	}
	
	public void pergunta5OnClick(){
		if(isPergunta5Disable()){
			setPergunta5Disable(false);
		}else{
			setPergunta5Disable(true);
		}
	}
	
	public void carregaCategoriasNv0(){
		categoriasNv0 = categoriaNv0Dao.buscarTodos();
	}
	
	public void carregaCategoriasNv1(){
		categoriasNv1 = categoriaNv1Dao.buscaPorCategoriaPai(categoriaNv0);
		if(categoriasNv1.isEmpty()){
			setRenderizaMsg(true);
		}else{
			setRenderizaMsg(false);
		}
	}
	
	
	public void upload(FileUploadEvent event){
		UploadedFile arquivo = event.getFile(); //peguei arquivo
		try {
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivo.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			diretorioTemp = arquivoTemp.toString();
			System.out.println(diretorioTemp);
			Path origem = Paths.get(diretorioTemp);
			String diretorioPc = "/home/alan/Dropbox/projetos/tcc-fap/src/main/webapp/resources/imgs/";
			String diretorioWildfly = "/home/alan/wildfly-8.2.0.Final/standalone/deployments/tcc-fap.war/resources/imgs/";
			String nomeDaImagem = "1" + new MD5Crypt().criptografar(usuarioLogado.getUsuario().getUsuario() + Calendar.getInstance().toString()) + ".jpg";
			Path destinoEclipse = Paths.get(diretorioPc + nomeDaImagem);
			Path destinoWildfly = Paths.get(diretorioWildfly + nomeDaImagem);
			String diretorioDoProjeto = "/imgs/"+nomeDaImagem;
			try {
				Files.copy(origem, destinoEclipse, StandardCopyOption.REPLACE_EXISTING);
				Files.copy(origem, destinoWildfly, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				new Mensagens().falhaUpload();
			}
			imagem.setDataUpload(Calendar.getInstance());
			imagem.setDiretorio(diretorioDoProjeto);
			setMostraImagem1(true);
			setMostraUpload2(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void upload2(FileUploadEvent event){
		UploadedFile arquivo = event.getFile(); //peguei arquivo
		try {
			Path arquivoTemp2 = Files.createTempFile(null, null);
			Files.copy(arquivo.getInputstream(), arquivoTemp2, StandardCopyOption.REPLACE_EXISTING);
			diretorioTemp2 = arquivoTemp2.toString();
			System.out.println(diretorioTemp2);
			Path origem = Paths.get(diretorioTemp2);
			String diretorioPc = "/home/alan/Dropbox/projetos/tcc-fap/src/main/webapp/resources/imgs/";
			String diretorioWildfly = "/home/alan/wildfly-8.2.0.Final/standalone/deployments/tcc-fap.war/resources/imgs/";
			String nomeDaImagem = "2" + new MD5Crypt().criptografar(usuarioLogado.getUsuario().getUsuario() + Calendar.getInstance().toString()) + ".jpg";
			Path destinoEclipse = Paths.get(diretorioPc + nomeDaImagem);
			Path destinoWildfly = Paths.get(diretorioWildfly + nomeDaImagem);
			String diretorioDoProjeto = "/imgs/"+nomeDaImagem;
			try {
				Files.copy(origem, destinoEclipse, StandardCopyOption.REPLACE_EXISTING);
				Files.copy(origem, destinoWildfly, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				new Mensagens().falhaUpload();
			}
			setMostraImagem2(true);
			setMostraUpload3(true);
			imagem2.setDataUpload(Calendar.getInstance());
			imagem2.setDiretorio(diretorioDoProjeto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void upload3(FileUploadEvent event){
		UploadedFile arquivo = event.getFile(); //peguei arquivo
		try {
			Path arquivoTemp3 = Files.createTempFile(null, null);
			Files.copy(arquivo.getInputstream(), arquivoTemp3, StandardCopyOption.REPLACE_EXISTING);
			diretorioTemp3 = arquivoTemp3.toString();
			System.out.println(diretorioTemp3);
			Path origem = Paths.get(diretorioTemp3);
			String diretorioPc = "/home/alan/Dropbox/projetos/tcc-fap/src/main/webapp/resources/imgs/";
			String diretorioWildfly = "/home/alan/wildfly-8.2.0.Final/standalone/deployments/tcc-fap.war/resources/imgs/";
			String nomeDaImagem = "3" + new MD5Crypt().criptografar(usuarioLogado.getUsuario().getUsuario() + Calendar.getInstance().toString()) + ".jpg";
			Path destinoEclipse = Paths.get(diretorioPc + nomeDaImagem);
			Path destinoWildfly = Paths.get(diretorioWildfly + nomeDaImagem);
			String diretorioDoProjeto = "/imgs/"+nomeDaImagem;
			try {
				Files.copy(origem, destinoEclipse, StandardCopyOption.REPLACE_EXISTING);
				Files.copy(origem, destinoWildfly, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				new Mensagens().falhaUpload();
			}
			setMostraImagem3(true);
			setMostraUpload4(true);
			imagem3.setDataUpload(Calendar.getInstance());
			imagem3.setDiretorio(diretorioDoProjeto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void upload4(FileUploadEvent event){
		UploadedFile arquivo = event.getFile(); //peguei arquivo
		try {
			Path arquivoTemp4 = Files.createTempFile(null, null);
			Files.copy(arquivo.getInputstream(), arquivoTemp4, StandardCopyOption.REPLACE_EXISTING);
			diretorioTemp4 = arquivoTemp4.toString();
			System.out.println(diretorioTemp4);
			Path origem = Paths.get(diretorioTemp4);
			String diretorioPc = "/home/alan/Dropbox/projetos/tcc-fap/src/main/webapp/resources/imgs/";
			String diretorioWildfly = "/home/alan/wildfly-8.2.0.Final/standalone/deployments/tcc-fap.war/resources/imgs/";
			String nomeDaImagem = "4" + new MD5Crypt().criptografar(usuarioLogado.getUsuario().getUsuario() + Calendar.getInstance().toString()) + ".jpg";
			Path destinoEclipse = Paths.get(diretorioPc + nomeDaImagem);
			Path destinoWildfly = Paths.get(diretorioWildfly + nomeDaImagem);
			String diretorioDoProjeto = "/imgs/"+nomeDaImagem;
			try {
				Files.copy(origem, destinoEclipse, StandardCopyOption.REPLACE_EXISTING);
				Files.copy(origem, destinoWildfly, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				new Mensagens().falhaUpload();
			}
			setMostraImagem4(true);
			imagem4.setDataUpload(Calendar.getInstance());
			imagem4.setDiretorio(diretorioDoProjeto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void carregaCidades(){
		if(estado == null){
			setDisableComboBox(true);
		}else{
			setDisableComboBox(false);
		}
		cidades = cidadeDao.buscaCidadePorEstado(estado);
	}
	
	//===================================================BEGINvalidadores======================================================
	public void validaPagamento(FacesContext context, UIComponent toValidate, Object value)throws ValidatorException{
		if((!this.anuncio.isBoleto()) || (!this.anuncio.isCartaoCredito()) || (!this.anuncio.isDinheiro()) || 
				(!this.anuncio.isCartaoDebito())){
			FacesMessage message = new FacesMessage("Você deve escolher ao menos um meio de pagamento");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
		
	}
	
	
	public void validaFotoObrigatoria(FacesContext context, UIComponent toValidate, Object value)throws ValidatorException{
		if(this.imagem.getDiretorio().isEmpty()){
			FacesMessage message = new FacesMessage("Você deve inserir ao menos uma foto");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public void validaPergunta1(FacesContext context, UIComponent toValidate, Object value){
		String pergunta = (String) value;
		if(pergunta.trim().equals("") || pergunta == null){
			FacesMessage message = new FacesMessage("Pergunta deve ser preenchida.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}else if(!this.pergunta1Disable && pergunta.length() < 8){
			FacesMessage message = new FacesMessage("Pergunta com " + pergunta.length() + "/8 caracteres obrigatórios");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public void validaPergunta2(FacesContext context, UIComponent toValidate, Object value){
		String pergunta = (String) value;
		if(pergunta.trim().equals("") || pergunta == null){
			FacesMessage message = new FacesMessage("Pergunta deve ser preenchida.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}else if(!this.pergunta2Disable && pergunta.length() < 8){
			FacesMessage message = new FacesMessage("Pergunta com " + pergunta.length() + "/8 caracteres obrigatórios");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public void validaPergunta3(FacesContext context, UIComponent toValidate, Object value){
		String pergunta = (String) value;
		if(pergunta.trim().equals("") || pergunta == null){
			FacesMessage message = new FacesMessage("Pergunta deve ser preenchida.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}else if(!this.pergunta3Disable && pergunta.length() < 8){
			FacesMessage message = new FacesMessage("Pergunta com " + pergunta.length() + "/8 caracteres obrigatórios");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public void validaPergunta4(FacesContext context, UIComponent toValidate, Object value){
		String pergunta = (String) value;
		if(pergunta.trim().equals("") || pergunta == null){
			FacesMessage message = new FacesMessage("Pergunta deve ser preenchida.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}else if(!this.pergunta4Disable && pergunta.length() < 8){
			FacesMessage message = new FacesMessage("Pergunta com " + pergunta.length() + "/8 caracteres obrigatórios");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public void validaPergunta5(FacesContext context, UIComponent toValidate, Object value){
		String pergunta = (String) value;
		if(pergunta.trim().equals("") || pergunta == null){
			FacesMessage message = new FacesMessage("Pergunta deve ser preenchida.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}else if(!this.pergunta5Disable && pergunta.length() < 8){
			FacesMessage message = new FacesMessage("Pergunta com " + pergunta.length() + "/8 caracteres obrigatórios");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	//===================================================ENDvalidadores======================================================
	
	public void carregaEstados(){
		this.estados = estadoDao.buscarTodos();
	}
	
	public void limpar(){
		this.anuncio = new Anuncio();
		this.categoriaNv0 = new CategoriaNv0();
	}
	
	public Anuncio getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	
	public List<CategoriaNv0> getCategoriasNv0() {
		return categoriasNv0;
	}
	public void setCategoriasNv0(List<CategoriaNv0> categoriasNv0) {
		this.categoriasNv0 = categoriasNv0;
	}
	
	public CategoriaNv0 getCategoriaNv0() {
		return categoriaNv0;
	}
	public void setCategoriaNv0(CategoriaNv0 categoriaNv0) {
		this.categoriaNv0 = categoriaNv0;
	}
	
	public List<CategoriaNv1> getCategoriasNv1() {
		return categoriasNv1;
	}
	public void setCategoriasNv1(List<CategoriaNv1> categoriasNv1) {
		this.categoriasNv1 = categoriasNv1;
	}
	
	public boolean isRenderizaMsg() {
		return renderizaMsg;
	}
	public void setRenderizaMsg(boolean renderizaMsg) {
		this.renderizaMsg = renderizaMsg;
	}
	
	public CategoriaNv1 getCategoriaNv1() {
		return categoriaNv1;
	}
	public void setCategoriaNv1(CategoriaNv1 categoriaNv1) {
		this.categoriaNv1 = categoriaNv1;
	}
	
	public Entrada[] getEntrada() {
		return Entrada.values();
	}
	
	public Parcela[] getParcela(){
		return Parcela.values();
	}
	
	public TipoDeDado[] getTipoDeDado(){
		return TipoDeDado.values();
	}
	
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public boolean isDisableComboBox() {
		return disableComboBox;
	}
	public void setDisableComboBox(boolean disableComboBox) {
		this.disableComboBox = disableComboBox;
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

	public boolean isPergunta1Disable() {
		return pergunta1Disable;
	}

	public void setPergunta1Disable(boolean pergunta1Disable) {
		this.pergunta1Disable = pergunta1Disable;
	}

	public boolean isPergunta2Disable() {
		return pergunta2Disable;
	}

	public void setPergunta2Disable(boolean pergunta2Disable) {
		this.pergunta2Disable = pergunta2Disable;
	}

	public boolean isPergunta3Disable() {
		return pergunta3Disable;
	}

	public void setPergunta3Disable(boolean pergunta3Disable) {
		this.pergunta3Disable = pergunta3Disable;
	}

	public boolean isPergunta4Disable() {
		return pergunta4Disable;
	}

	public void setPergunta4Disable(boolean pergunta4Disable) {
		this.pergunta4Disable = pergunta4Disable;
	}

	public boolean isPergunta5Disable() {
		return pergunta5Disable;
	}

	public void setPergunta5Disable(boolean pergunta5Disable) {
		this.pergunta5Disable = pergunta5Disable;
	}
	
	public boolean isRenderizaParcelamentoMax() {
		return renderizaParcelamentoMax;
	}
	public void setRenderizaParcelamentoMax(boolean renderizaParcelamentoMax) {
		this.renderizaParcelamentoMax = renderizaParcelamentoMax;
	}
	
	public boolean isRenderizaWizard() {
		return renderizaWizard;
	}
	public void setRenderizaWizard(boolean renderizaWizard) {
		this.renderizaWizard = renderizaWizard;
	}
	
	public UsuarioLogadoBean getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(UsuarioLogadoBean usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public ImagemAnuncio getImagem() {
		return imagem;
	}
	public void setImagem(ImagemAnuncio imagem) {
		this.imagem = imagem;
	}

	public ImagemAnuncio getImagem2() {
		return imagem2;
	}
	public void setImagem2(ImagemAnuncio imagem2) {
		this.imagem2 = imagem2;
	}

	public ImagemAnuncio getImagem3() {
		return imagem3;
	}
	public void setImagem3(ImagemAnuncio imagem3) {
		this.imagem3 = imagem3;
	}

	public ImagemAnuncio getImagem4() {
		return imagem4;
	}
	public void setImagem4(ImagemAnuncio imagem4) {
		this.imagem4 = imagem4;
	}

	public boolean isMostraImagem1() {
		return mostraImagem1;
	}
	public void setMostraImagem1(boolean mostraImagem1) {
		this.mostraImagem1 = mostraImagem1;
	}

	public boolean isMostraImagem2() {
		return mostraImagem2;
	}
	public void setMostraImagem2(boolean mostraImagem2) {
		this.mostraImagem2 = mostraImagem2;
	}

	public boolean isMostraImagem3() {
		return mostraImagem3;
	}
	public void setMostraImagem3(boolean mostraImagem3) {
		this.mostraImagem3 = mostraImagem3;
	}
	
	public boolean isMostraImagem4() {
		return mostraImagem4;
	}
	public void setMostraImagem4(boolean mostraImagem4) {
		this.mostraImagem4 = mostraImagem4;
	}

	public boolean isMostraUpload2() {
		return mostraUpload2;
	}

	public void setMostraUpload2(boolean mostraUpload2) {
		this.mostraUpload2 = mostraUpload2;
	}

	public boolean isMostraUpload3() {
		return mostraUpload3;
	}

	public void setMostraUpload3(boolean mostraUpload3) {
		this.mostraUpload3 = mostraUpload3;
	}

	public boolean isMostraUpload4() {
		return mostraUpload4;
	}

	public void setMostraUpload4(boolean mostraUpload4) {
		this.mostraUpload4 = mostraUpload4;
	}
}