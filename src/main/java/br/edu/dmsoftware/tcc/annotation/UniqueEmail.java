package br.edu.dmsoftware.tcc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.edu.dmsoftware.tcc.validator.UniqueEmailValidator;
//Linkando classe validadora
@Constraint(validatedBy = {UniqueEmailValidator.class})
//Essa constraint pode ser utilizado somente por catributos e métodos
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueEmail {
	// Mensagem quando o e-mail existir
	String message() default "Este e-mail já está em uso";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
