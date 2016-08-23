package br.edu.dmsoftware.tcc.infra;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagens {
	
	public void salvoComSucesso(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dado salvo com sucesso!", null));
	}
	
	public void excluidoComSucesso(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dado removido com sucesso!", null));
	}
	
	public void falhaAoSalvar(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO - Ocorreu um erro ao tentar salvar!", null));
	}
	
	public void falhaAoExcluir(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO - Ocorreu um erro ao tentar exluir!", null));
	}
	
	public void naoHaDados(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não há dados para excluir!", null));
	}
	
	public void erro(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ocorreu um erro, tente novamente", null));
	}
	
	public void confirmadoSucesso(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta confirmada com sucesso", null));
	}
	
	public void falhaAutenticacao(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Usuário e/ou senha incorretos", null));
	}
	
	public void paginaNaoEncontrada(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Página não encontrada", null));
	}
	
	public void contaNaoConfirmada(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Você precisa confirmar sua conta de acesso", null));
	}
	
	public void	logadoComSucesso(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Logado com sucesso", null));
	}
	
}
