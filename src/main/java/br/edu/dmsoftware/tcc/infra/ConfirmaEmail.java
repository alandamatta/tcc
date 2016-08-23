package br.edu.dmsoftware.tcc.infra;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.edu.dmsoftware.tcc.modelo.Usuario;

public class ConfirmaEmail {

	private EmailUtil email = new EmailUtil();
	private CodGenerator gerador = new CodGenerator();
	private String codigo;
	private String mensagem;
	private String link;
	
	public void enviarConfirmacao(Usuario usuario) throws AddressException, MessagingException{
		link = "localhost:8080/tcc-fap/confirmacao.jsf?C="+usuario.getId()+"W"+usuario.getCodVerificacao();
		mensagem = "<html><head>"
				+ "<meta charset='UTF-8'/>"
				+ "</head>"
				+ "<body>"	
				+ "Ola. <br/> Clique no link para confirmar a conta: " + link + "<br/>"
				+ "Obrigado por utilizar nossos servicos."
				+ "</body></html>";
		this.email.enviarEmail(usuario.getEmail(), "Confirmação de cadastro", mensagem);
		usuario = new Usuario();
	}
	
}
