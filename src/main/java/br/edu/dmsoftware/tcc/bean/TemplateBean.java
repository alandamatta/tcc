package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.RequisicaoDao;
import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.MD5Crypt;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Nivel;
import br.edu.dmsoftware.tcc.modelo.Requisicao;
import br.edu.dmsoftware.tcc.modelo.Usuario;

@Model
@ViewScoped
public class TemplateBean implements Serializable{
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	
	private String txtBtnLogin;
	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private RequisicaoDao requisicaoDao;
	private List<Requisicao> requisicoesNaoNotificadas;
	
	private String usuario;
	private String senha;
	private boolean anuncianteItem;
	
	private String valueBtnLogin;
	
	private String pesquisa;
	
	private boolean ehAnunciante = false;
	
	@PostConstruct
	public void init(){
		if(usuarioLogado.isLogado()){
			setValueBtnLogin("Sair");
			if(usuarioLogado.getUsuario().getNivel().equals(Nivel.ANUNCIANTE)){
				setEhAnunciante(true);
			}else{
				setEhAnunciante(false);
			}
		}else{
			setValueBtnLogin("Entrar");
		}
		
	}
	
	public void notificacaoRequisicao(){
		this.requisicoesNaoNotificadas = requisicaoDao.requisicaoNaoNotificadaParaAnunciante(usuarioLogado.getUsuario());
	}
	
	public String autenticar(){
		try {
			if(usuarioDao.usuarioExiste(this.usuario)){
				Usuario usuario = usuarioDao.buscaPorUsuario(this.usuario) ;
				if(usuario.getUsuario().equals(this.usuario) && 
						(usuario.getSenha().equals(new MD5Crypt().criptografar(this.senha))) && (usuario.isEmailConfirmado())){
					//logado
					usuarioLogado.logar(usuario);
					new Mensagens().logadoComSucesso();
					return "";
					}else if(!usuario.isEmailConfirmado()){
					//email não confirmado
					new Mensagens().contaNaoConfirmada();
					return "";
				}else{
					//usuario ou senha incorretos
					new Mensagens().falhaAutenticacao();
					return "";
				}
			}else{
				//usuário nao existe
				new Mensagens().falhaAutenticacao();
				return "";
			}
		} catch (Exception e) {
			new Mensagens().falhaAutenticacao();
			return "";
		}
	}
	
	public void validateUsuario(FacesContext context, UIComponent toValidate, Object value){
		String usuario = (String) value;
		if(usuario.equals(null) || (usuario.trim().equals(""))){
			FacesMessage message = new FacesMessage("Usuário deve estar preenchido");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public void validateSenha(FacesContext context, UIComponent toValidate, Object value){
		String senha = (String) value;
		if(senha.equals(null) || senha.trim().equals("")){
			FacesMessage message = new FacesMessage("Senha deve estar preenchida");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public String pesquisar(){
		return "/pesquisa.jsf?faces-redirect=true&pesquisa="+this.pesquisa;
	}
	
	public String deslogar(){
		if(usuarioLogado.isLogado()){
			usuarioLogado.deslogar();
			return "login.jsf?faces-redirect=true";
		}else{
			return "";
		}
	}
	
	public String acaoBtnLogar(){
		if(usuarioLogado.isLogado()){
			usuarioLogado.deslogar();
			return "/login.jsf?faces-redirect=true";
		}else{
			return "/login.jsf?faces-redirect=true";
		}
	}
	
	public String getTxtBtnLogin() {
		return txtBtnLogin;
	}
	public void setTxtBtnLogin(String txtBtnLogin) {
		this.txtBtnLogin = txtBtnLogin;
	}
	
	public UsuarioLogadoBean getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(UsuarioLogadoBean usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public boolean isAnuncianteItem() {
		return anuncianteItem;
	}
	public void setAnuncianteItem(boolean anuncianteItem) {
		this.anuncianteItem = anuncianteItem;
	}
	
	public String getPesquisa() {
		return pesquisa;
	}
	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getValueBtnLogin() {
		return valueBtnLogin;
	}
	public void setValueBtnLogin(String valueBtnLogin) {
		this.valueBtnLogin = valueBtnLogin;
	}
	
	public boolean isEhAnunciante() {
		return ehAnunciante;
	}
	public void setEhAnunciante(boolean ehAnunciante) {
		this.ehAnunciante = ehAnunciante;
	}
	
	public List<Requisicao> getRequisicoesNaoNotificadas() {
		return requisicoesNaoNotificadas;
	}
	public void setRequisicoesNaoNotificadas(List<Requisicao> requisicoesNaoNotificadas) {
		this.requisicoesNaoNotificadas = requisicoesNaoNotificadas;
	}
}
