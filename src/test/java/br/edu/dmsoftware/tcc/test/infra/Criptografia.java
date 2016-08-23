package br.edu.dmsoftware.tcc.test.infra;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

public class Criptografia {
	
	@Test
	public void criptografar(){
		String senhaSemCriptografar = "alandamatta";
		SimpleHash hash = new SimpleHash("md5", senhaSemCriptografar);
		String senhaCriptografia = hash.toHex();
		System.out.println(senhaCriptografia);
		SimpleHash hash2 = new SimpleHash("md5", senhaSemCriptografar);
		System.out.println(hash.toHex());
	}
}
