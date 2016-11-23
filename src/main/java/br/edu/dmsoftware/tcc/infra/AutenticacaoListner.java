package br.edu.dmsoftware.tcc.infra;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

import org.omnifaces.util.Faces;

import br.edu.dmsoftware.tcc.bean.UsuarioLogadoBean;
import br.edu.dmsoftware.tcc.modelo.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListner implements PhaseListener{
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	
	@Override
	public void afterPhase(PhaseEvent arg0) {
		String paginaAtual = Faces.getViewId().toString();
		
		List<String> paginasLiberadas = new ArrayList<String>();
		paginasLiberadas.add("/login.xhtml");
		paginasLiberadas.add("/pesquisa.xhtml");
		paginasLiberadas.add("/confirmacao.xhtml");
		paginasLiberadas.add("/cadastrar.xhtml");
		paginasLiberadas.add("/anuncio.xhtml");
		
		boolean ehPaginaLiberada = paginasLiberadas.contains(paginaAtual);
	
		if(!ehPaginaLiberada){
			System.out.println("primeiro if deu true");
			if(usuarioLogado.getUsuario() == null){
				Faces.navigate("/login.jsf");
				return;
			}
			
			Usuario usuario = usuarioLogado.getUsuario();
			if(usuario == null){
				Faces.navigate("/login.jsf");
				return;
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	
	

}
