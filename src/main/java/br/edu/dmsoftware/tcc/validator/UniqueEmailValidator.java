package br.edu.dmsoftware.tcc.validator;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.dmsoftware.tcc.annotation.UniqueEmail;
import br.edu.dmsoftware.tcc.dao.UsuarioDao;
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
	
	@Inject
	UsuarioDao usuarioDao;
	
	@Override
	public void initialize(UniqueEmail constraintAnnotation) {}
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if(usuarioDao.emailExiste(email)){
			return false;
		}
		return true;
	}
}
