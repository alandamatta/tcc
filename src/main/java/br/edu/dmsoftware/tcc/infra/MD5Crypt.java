package br.edu.dmsoftware.tcc.infra;

import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5Crypt {
	
	public String criptografar(String valor){
		SimpleHash hash = new SimpleHash("md5", valor);
		return hash.toHex();
	}
	
}
