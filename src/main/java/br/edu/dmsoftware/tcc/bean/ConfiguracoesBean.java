package br.edu.dmsoftware.tcc.bean;

import java.io.Serializable;
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

import br.edu.dmsoftware.tcc.dao.CidadeDao;
import br.edu.dmsoftware.tcc.dao.EstadoDao;
import br.edu.dmsoftware.tcc.dao.PessoaDao;
import br.edu.dmsoftware.tcc.dao.UsuarioDao;
import br.edu.dmsoftware.tcc.infra.MD5Crypt;
import br.edu.dmsoftware.tcc.infra.Mensagens;
import br.edu.dmsoftware.tcc.modelo.Cidade;
import br.edu.dmsoftware.tcc.modelo.Estado;
import br.edu.dmsoftware.tcc.modelo.Pessoa;
import br.edu.dmsoftware.tcc.modelo.Usuario;

@Model
@ViewScoped
public class ConfiguracoesBean implements Serializable{
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private Usuario usuario;
	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private Pessoa pessoa;
	@Inject
	private PessoaDao pessoaDao;
	@Inject
	private EstadoDao estadoDao;
	@Inject
	private Estado estado;
	private List<Estado> estados;
	@Inject
	private CidadeDao cidadeDao;
	private List<Cidade> cidades;
	private String senhaAntiga;
	
	private boolean disabledCidade = true;
	private boolean disableEndereco = true;
	
	@PostConstruct
	public void init(){
		if(usuarioLogado.isLogado()){
			carregarEstados();
			carregarUsuario();
		}
		if(pessoaDao.pessoaExiste(usuarioLogado.getUsuario())){
			carregarUsuario();
			this.pessoa = usuarioLogado.getUsuario().getPessoa();
			this.estado = usuarioLogado.getUsuario().getPessoa().getCidade().getEstado();
		}
	}
	
	@Transactional
	public void salvarPessoa(){
		try {
			this.pessoa.setDataCadastro(Calendar.getInstance());
			this.pessoa.setUsuario(usuarioLogado.getUsuario());
			this.pessoaDao.salvar(pessoa);
			new Mensagens().salvoComSucesso();
		} catch (Exception e) {
			new Mensagens().acaoEfetuadaComErro();
		}
	}
	
	@Transactional
	public void salvarUsuario(){
		try {
			this.usuario.setSenha(new MD5Crypt().criptografar(this.usuario.getSenha()));
			usuarioDao.salvar(this.usuario);
			new Mensagens().senhaAlterada();
		} catch (Exception e) {
			new Mensagens().acaoEfetuadaComErro();
		}
	}
	
	public void carregarUsuario(){
		this.usuario = usuarioLogado.getUsuario();
	}
	
	public void carregarEstados(){
		setEstados(estadoDao.buscarTodos());
	}
	
	public void carregarCidades(){
		setCidades(cidadeDao.buscaCidadePorEstado(this.estado));
		if(getEstado() != null){
			setDisabledCidade(false);
		}else{
			setDisabledCidade(true);
		}
		if((this.estado != null)&&(this.pessoa.getCidade() != null)){
			setDisableEndereco(false);
		}else{
			setDisableEndereco(true);
		}
	}
	
	public void cidadeAjax(){
		if((this.estado != null) && (this.pessoa.getCidade() != null)){
			setDisableEndereco(false);
		}else{
			setDisableEndereco(true);
		}
	}
	
	public void validaSenhaAtual(FacesContext context, UIComponent toValidate, Object value){
		String senha = new MD5Crypt().criptografar((String) value);
		if(!senha.equals(usuarioLogado.getUsuario().getSenha())){
			FacesMessage message = new FacesMessage("Senha incorreta");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
	
	public UsuarioLogadoBean getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(UsuarioLogadoBean usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public boolean isDisabledCidade() {
		return disabledCidade;
	}
	public void setDisabledCidade(boolean disabledCidade) {
		this.disabledCidade = disabledCidade;
	}
	
	public boolean isDisableEndereco() {
		return disableEndereco;
	}
	public void setDisableEndereco(boolean disableEndereco) {
		this.disableEndereco = disableEndereco;
	}
	
	public String getSenhaAntiga() {
		return senhaAntiga;
	}
	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
