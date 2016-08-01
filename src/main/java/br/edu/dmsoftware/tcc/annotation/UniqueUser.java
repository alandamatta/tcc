package br.edu.dmsoftware.tcc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.edu.dmsoftware.tcc.validator.UniqueUserValidator;
//Linkar classe validadora
@Constraint(validatedBy= {UniqueUserValidator.class})
//onde a anotação poderá ser utilziada
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueUser {
	String message() default "Este usuário já está em uso";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
