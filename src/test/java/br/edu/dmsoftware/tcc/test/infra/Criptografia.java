package br.edu.dmsoftware.tcc.test.infra;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

import br.edu.dmsoftware.tcc.infra.MD5Crypt;

public class Criptografia {
	
	@Test
	public void criptografar(){
		String senha = new MD5Crypt().criptografar("123456");
		System.out.println(senha);
	}
}
