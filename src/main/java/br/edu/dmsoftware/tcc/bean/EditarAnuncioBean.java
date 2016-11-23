package br.edu.dmsoftware.tcc.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.List;

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
import br.edu.dmsoftware.tcc.dao.CidadeDao;
import br.edu.dmsoftware.tcc.dao.EstadoDao;
import br.edu.dmsoftware.tcc.dao.ImagemAnuncioDao;
import br.edu.dmsoftware.tcc.dao.PerguntaDao;
import br.edu.dmsoftware.tcc.infra.MD5Crypt;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Anuncio;
import br.edu.dmsoftware.tcc.modelo.Cidade;
import br.edu.dmsoftware.tcc.modelo.Entrada;
import br.edu.dmsoftware.tcc.modelo.Estado;
import br.edu.dmsoftware.tcc.modelo.ImagemAnuncio;
import br.edu.dmsoftware.tcc.modelo.Parcela;
import br.edu.dmsoftware.tcc.modelo.Pergunta;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.TipoDeDado;

@Model
@ViewScoped
public class EditarAnuncioBean implements Serializable{
	
	private Long idDoAnuncio;
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
	private PerguntaDao perguntaDao;
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
	private List<Pergunta> perguntas;
	@Inject
	private ImagemAnuncioDao imagemAnuncioDao;
	private List<ImagemAnuncio> imagensAnuncio;
	@Inject
	private ImagemAnuncio imagem;
	@Inject
	private ImagemAnuncio imagem2;
	@Inject
	private ImagemAnuncio imagem3;
	@Inject
	private ImagemAnuncio imagem4;
	
	
	private String diretorioTemp;
	private String diretorioTemp2;
	private String diretorioTemp3;
	private String diretorioTemp4;
	
	
	private boolean msgPaginaInexistente;
	private boolean mostraAnuncio;
	private boolean mostraRegiao = false;
	private boolean cidadeDisable = true;
	private boolean pergunta1Disable = true;
	private boolean pergunta2Disable = true;
	private boolean pergunta3Disable = true;
	private boolean pergunta4Disable = true;
	private boolean pergunta5Disable = true;
	private boolean mostraParcelas = false;
	
	private boolean mostraImagem1 = false;
	private boolean mostraImagem2 = false;
	private boolean mostraImagem3 = false;
	private boolean mostraImagem4 = false;
	
	public void init(){}
	
	@Transactional
	public void salvar(){
		try {
			this.anuncioDao.salvar(this.anuncio);
			if(this.imagem.getId() != null){
				imagem.setDataUpload(Calendar.getInstance());
				imagemAnuncioDao.salvar(imagem);
			}else if(this.imagem.getDiretorio() != null){
				imagem.setAnuncio(this.anuncio);
				imagem.setDataUpload(Calendar.getInstance());
				imagemAnuncioDao.salvar(imagem);
			}
			
			if(this.imagem2.getId() != null){
				imagem2.setDataUpload(Calendar.getInstance());
				imagemAnuncioDao.salvar(imagem2);
			}else if(this.imagem2.getDiretorio() != null){
				imagem2.setAnuncio(this.anuncio);
				imagem2.setDataUpload(Calendar.getInstance());
				imagemAnuncioDao.salvar(imagem2);
			}
			
			if(this.imagem3.getId() != null){
				imagem3.setDataUpload(Calendar.getInstance());
				imagemAnuncioDao.salvar(imagem3);
			}else if(this.imagem3.getDiretorio() != null){
				imagem3.setAnuncio(this.anuncio);
				imagem3.setDataUpload(Calendar.getInstance());
				imagemAnuncioDao.salvar(imagem3);
			}
			
			if(this.imagem4.getId() != null){
				imagem4.setDataUpload(Calendar.getInstance());
				imagemAnuncioDao.salvar(imagem4);
			}else if(this.imagem4.getDiretorio() != null){
				imagem4.setAnuncio(this.anuncio);
				imagem4.setDataUpload(Calendar.getInstance());
				imagemAnuncioDao.salvar(imagem4);
			}
		} catch (Exception e) {
			new Mensagens().acaoEfetuadaComErro();
		}
	}
	
	public void carregarAnuncio(){
		if(!FacesContext.getCurrentInstance().isPostback()){
			try {
				if(usuarioLogado.isLogado()){
					anuncio = anuncioDao.buscaPeloId(idDoAnuncio);
					if(!anuncio.getSituacao().getValor().equals(Situacao.INATIVO.getValor())){
						if(anuncio.getUsuario().equals(anuncio.getUsuario())){
							//o usuario pode alterar o anuncio, pq o anuncio é dele
							setMostraAnuncio(true);
							setMsgPaginaInexistente(false);
							carregarEstadoDoAnuncio();
							carregarEstados();
							carregarCidades();
							carregarPerguntas();
							carregarImagens();
						}else{
							//usuário nao pode alterar o anuncio, pq o anúncio nao é dele
							limparAnuncio();
							setMostraAnuncio(false);
							setMsgPaginaInexistente(true);
						}
					}else{
						setMostraAnuncio(false);
						setMsgPaginaInexistente(true);
					}
				}else{
					//usuario nao está logado
					setMostraAnuncio(false);
					setMsgPaginaInexistente(true);
				}
			} catch (Exception e) {
				setMostraAnuncio(false);
				setMsgPaginaInexistente(true);
			}
			
		}
	}
	
	public void carregarPerguntas(){
		this.perguntas = perguntaDao.perguntasDoAnuncio(this.anuncio);
		for (Pergunta pergunta : perguntas) {
			if(this.pergunta1.getId() == null){
				this.pergunta1 = pergunta;
				this.pergunta1Disable = !pergunta1.isAtiva();
			}else if(this.pergunta2.getId() == null){
				this.pergunta2 = pergunta;
				this.pergunta2Disable = !pergunta2.isAtiva();
			}else if(this.pergunta3.getId() == null){
				this.pergunta3 = pergunta;
				this.pergunta3Disable = !pergunta3.isAtiva();
			}else if(this.pergunta4.getId() == null){
				this.pergunta4 = pergunta;
				this.pergunta4Disable = !pergunta4.isAtiva();
			}else if(this.pergunta5.getId() == null){
				this.pergunta5 = pergunta;
				this.pergunta5Disable = !pergunta5.isAtiva();
			}
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
	
	private void carregarEstadoDoAnuncio(){
		this.estado = this.anuncio.getCidade().getEstado();
	}
	
	private void carregarEstados(){
		this.estados = estadoDao.buscarTodos();
	}
	
	public void carregarCidades(){
		this.cidades = cidadeDao.buscaCidadePorEstado(this.estado);
	}
	
	public void carregarImagens(){
		this.imagensAnuncio = imagemAnuncioDao.porAnuncio(this.anuncio);
		for (ImagemAnuncio imagem : this.imagensAnuncio) {
			if(this.imagem.getId() == null){
				this.imagem = imagem;
				setMostraImagem1(true);
			}else if(this.imagem2.getId() == null){
				this.imagem2 = imagem;
				setMostraImagem2(true);
			}else if(this.imagem3.getId() == null){
				this.imagem3 = imagem;
				setMostraImagem3(true);
			}else if(this.imagem4.getId() == null){
				this.imagem4 = imagem;
				setMostraImagem4(true);
			}
		}
	}
	
	public void limparAnuncio(){
		this.anuncio = new Anuncio();
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
	
	public void parcelaAjax(){
		if(this.anuncio.getParcela().equals(Parcela.SIM)){
			setMostraParcelas(true);
		}else{
			setMostraParcelas(false);
		}
	}
	
	//===================================================BEGINvalidadores======================================================
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
		
	
	public Long getIdDoAnuncio() {
		return idDoAnuncio;
	}
	public void setIdDoAnuncio(Long idDoAnuncio) {
		this.idDoAnuncio = idDoAnuncio;
	}
	
	public Anuncio getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	
	public boolean isMostraAnuncio() {
		return mostraAnuncio;
	}
	public void setMostraAnuncio(boolean mostraAnuncio) {
		this.mostraAnuncio = mostraAnuncio;
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
	
	public boolean isCidadeDisable() {
		return cidadeDisable;
	}
	public void setCidadeDisable(boolean cidadeDisable) {
		this.cidadeDisable = cidadeDisable;
	}
	
	public boolean isMsgPaginaInexistente() {
		return msgPaginaInexistente;
	}
	public void setMsgPaginaInexistente(boolean msgPaginaInexistente) {
		this.msgPaginaInexistente = msgPaginaInexistente;
	}
	
	public boolean isMostraRegiao() {
		return mostraRegiao;
	}
	public void setMostraRegiao(boolean mostraRegiao) {
		this.mostraRegiao = mostraRegiao;
	}
	
	public Entrada[] getEntrada() {
		return Entrada.values();
	}
	
	public Parcela[] getParcela(){
		return Parcela.values();
	}
	
	public List<Pergunta> getPerguntas() {
		return perguntas;
	}
	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
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
	
	public TipoDeDado[] getTipoDeDado(){
		return TipoDeDado.values();
	}
	
	public boolean isMostraParcelas() {
		return mostraParcelas;
	}
	public void setMostraParcelas(boolean mostraParcelas) {
		this.mostraParcelas = mostraParcelas;
	}
	
	public List<ImagemAnuncio> getImagensAnuncio() {
		return imagensAnuncio;
	}
	public void setImagensAnuncio(List<ImagemAnuncio> imagensAnuncio) {
		this.imagensAnuncio = imagensAnuncio;
	}

	public String getDiretorioTemp() {
		return diretorioTemp;
	}

	public void setDiretorioTemp(String diretorioTemp) {
		this.diretorioTemp = diretorioTemp;
	}

	public String getDiretorioTemp2() {
		return diretorioTemp2;
	}

	public void setDiretorioTemp2(String diretorioTemp2) {
		this.diretorioTemp2 = diretorioTemp2;
	}

	public String getDiretorioTemp3() {
		return diretorioTemp3;
	}

	public void setDiretorioTemp3(String diretorioTemp3) {
		this.diretorioTemp3 = diretorioTemp3;
	}

	public String getDiretorioTemp4() {
		return diretorioTemp4;
	}

	public void setDiretorioTemp4(String diretorioTemp4) {
		this.diretorioTemp4 = diretorioTemp4;
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
}