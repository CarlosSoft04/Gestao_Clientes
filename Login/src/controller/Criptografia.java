package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {
	/**
	 * Constantes de algoritmo para algoritmos SHA-256 e MD5 que sao usadas para configurar o algoritmo de criptografia desejado
	 */
	private static final String SHA256 = "SHA-256";
	private static final String MD5 = "MD5";
	
	/**
	 * Informacao armazena a informacao a ser criptografada
	 * Padrao armazena o padrao de criptografia.(O algortimo de hash a ser usado)
	 */
	private String informacao;
	private String padrao;
	
	public Criptografia(String informacao, String padrao) {
		super();
		this.informacao = informacao;
		this.padrao = padrao;
	}

	public String getInformacao() {
		return informacao;
	}

	public void setInformacao(String informacao) {
		this.informacao = informacao;
	}

	public String getPadrao() {
		return padrao;
	}

	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}

	public static String getSha256() {
		return SHA256;
	}

	public static String getMd5() {
		return MD5;
	}
	
	
	public String criptografar() {
		//Obtem a informacao a ser criptografada
		String informacao = getInformacao();
		
		//Declara uma variavel do tipo 'MessageDigest' e inicializa uma Stringbuilder para armazenar o resultado em formato hexadecimal
		MessageDigest messageDigest;
		StringBuilder hexString = null;
		
		
		try {
			//Inicializa o messageDigest com o algoritmo especificado em padrao
			messageDigest = MessageDigest.getInstance(getPadrao());
			
			//Calcula o hash da informacao utilizando o UTF_8
			byte[] hash = messageDigest.digest(informacao.getBytes(StandardCharsets.UTF_8));
			
			//Inicializa o StringBuilder com o tamanho que eh o dobro do array hash
			hexString = new StringBuilder(2*hash.length);
			
			/**
			 * Converte cada byte  do Array para seu formato hexadecimal 
			 */
			for(int i = 0;i<hash.length;i++) {
				//Converte o byte atual para uma String hexadecimal
				String hex = Integer.toHexString(0xff & hash[i]); 
				
				//Adiciona um 0 a esquerda caso o hexadecimal tenha apenas um digito
				if(hex.length()==1) {
					hexString.append('0');
					 
				}
				hexString.append(hex);	
				}
			
		//Tratamento de excecao especifica para tratar casos em que o algoritmo especifico nao eh encontrado
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//Retorna o algoritmo criptografado em letras maiusculas
		return hexString.toString().toUpperCase();
		
		
			
		}
				
 		
		
	}
	
	
	
	
	
	
	
	
	
	


