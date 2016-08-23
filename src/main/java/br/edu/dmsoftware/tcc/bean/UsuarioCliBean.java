package br.edu.dmsoftware.tcc.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.transaction.Transactional;

import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.CodGenerator;
import br.edu.dmsoftware.tcc.infra.ConfirmaEmail;
import br.edu.dmsoftware.tcc.infra.MD5Crypt;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Nivel;
import br.edu.dmsoftware.tcc.modelo.Situacao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

@Model
public class UsuarioCliBean {
	
	Mensagens mensagem = new Mensagens();
	
	@Inject
	private UsuarioDao usuarioDao;
	
	private Usuario usuario = new Usuario();

	private List<Usuario> usuarios = new ArrayList<>();
	
	private ConfirmaEmail confirmaEmail = new ConfirmaEmail();
	
	private CodGenerator codGenerator = new CodGenerator();
	
	private String codigo;
	
	private String mensagemLoading;
	
	private String usuarioTemp;
	
	@PostConstruct
	public void init(){
		
	}
	
	@Transactional
	public void enviarEmail() throws AddressException, MessagingException{
		try {
			usuario.setCodVerificacao(codGenerator.gerarCodigo(usuario.getUsuario()));
			usuario.setDataCadastro(Calendar.getInstance());
			usuario.setEmailConfirmado(false);
			usuario.setNivel(Nivel.CONTRATANTE);
			usuario.setSituacao(Situacao.ATIVO);
			//criptografar senha
			usuario.setSenha(new MD5Crypt().criptografar(usuario.getSenha()));
			usuarioTemp = usuario.getUsuario();
			usuarioDao.salvar(usuario);
			usuario = usuarioDao.buscaPorUsuario(usuarioTemp);
			confirmaEmail.enviarConfirmacao(usuario);
			limpar();
		} catch (Exception e) {
			mensagem.erro();
		}
		
	}
	
	
	public void validateUser(FacesContext context, UIComponent toValidate, Object value){
		String usuario = (String) value;
		if(usuarioDao.usuarioExiste(usuario)){
			FacesMessage message = new FacesMessage("'" + usuario + "'" + " já está em uso");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public void validateEmail(FacesContext context, UIComponent toValidate, Object value){
		String email = (String) value;
		if(usuarioDao.emailExiste(email)){
			FacesMessage message = new FacesMessage("E-mail já está em uso");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public String chamaView(){
		return "";
	}
	
	public void limpar(){
		this.usuario = new Usuario();
	}
	
	public void carregaUsuarios(){
		usuarios = usuarioDao.buscarTodos();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public String getMensagemLoading() {
		return mensagemLoading;
	}
	public void setMensagemLoading(String mensagemLoading) {
		this.mensagemLoading = mensagemLoading;
	}
	
}
