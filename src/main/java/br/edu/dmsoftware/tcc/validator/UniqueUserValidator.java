package br.edu.dmsoftware.tcc.validator;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.dmsoftware.tcc.annotation.UniqueUser;
import br.edu.dmsoftware.tcc.dao.UsuarioDao;

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, String> {
	
	@Inject
	private UsuarioDao usuarioDao;

	@Override
	public void initialize(UniqueUser constraintAnnotation) {}

	@Override
	public boolean isValid(String usuario, ConstraintValidatorContext context) {
		if(usuarioDao.usuarioExiste(usuario)){
			return false;
		}
		return true;
	}

}
