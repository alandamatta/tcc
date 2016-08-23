package br.edu.dmsoftware.tcc.validator;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.edu.dmsoftware.tcc.dao.UsuarioDao;

@Model
public class Validate {
	@Inject
	UsuarioDao usuarioDao;
	public void validateUser(FacesContext context, UIComponent toValidate, Object value){
		String email = (String) value;
		if(usuarioDao.usuarioExiste(email)){
			FacesMessage message = new FacesMessage("Usuário já está em uso!");
			context.addMessage(toValidate.getClientId(context), message);
		}
	}
}
