package br.edu.dmsoftware.tcc.infra;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class CodGenerator {
	public String gerarCodigo(String usuario){
		//pega milisegundos
		Long numero = new Date().getTime();
		String stringNumero = numero.toString();
		String numero1 = stringNumero.substring(9,10);
		String numero2 = stringNumero.substring(12,13);
		String numero3 = stringNumero.substring(11,12);
		//pegou 3 numeros
		//Pegar letras
		String diferencial = usuario.substring(1, 2).toUpperCase();
		Random posicao = new Random();
		ArrayList<String> alfabeto = new ArrayList<>();
		alfabeto.add("A");
		alfabeto.add("B");
		alfabeto.add("C");
		alfabeto.add("D");
		alfabeto.add("E");
		alfabeto.add("F");
		alfabeto.add("G");
		alfabeto.add("H");
		alfabeto.add("I");
		alfabeto.add("J");
		alfabeto.add("K");
		alfabeto.add("L");
		alfabeto.add("M");
		alfabeto.add("N");
		alfabeto.add("O");
		alfabeto.add("P");
		alfabeto.add("Q");
		alfabeto.add("R");
		alfabeto.add("S");
		alfabeto.add("T");
		alfabeto.add("U");
		alfabeto.add("V");
		alfabeto.add("X");
		alfabeto.add("Y");
		alfabeto.add("Z");
		String letra1 = alfabeto.get(posicao.nextInt(26));
		String letra2 = alfabeto.get(posicao.nextInt(26));
		
		String codigoTemp = numero1 + letra2 + numero2 + letra1 + numero3 + diferencial;
		String codigo = "";
		Integer posicao2;
		for (int i = 0; i <= 5; i++) {
			posicao2 = posicao.nextInt(6);
			codigo += codigoTemp.substring(posicao2, posicao2 + 1);
		}
		return codigo;
	}
}
