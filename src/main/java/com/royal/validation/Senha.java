package com.royal.validation;

import java.util.Arrays;
import java.util.stream.Stream;


public class Senha {
	public static boolean valida(String senha){
		return (senha.length() >= 8) && Arrays.asList(charValidos).containsAll(Arrays.asList(senha.toCharArray()));
	}
	
	public static int forca(String senha){
		int forca = 0;
		boolean temNumero = false;
		boolean temLetraMaiuscula = false;
		boolean temLetraMinuscula = false;
		boolean temCaractereEspecial = false;
		char ultimoCaractere = 0;
		int repetidos = 0;
		
		for(int i = 0; i < senha.length(); i++){
			char ch = senha.charAt(i);
			
			if(ultimoCaractere == ch){
				repetidos++;
			}
			
			if(repetidos <= 3){
				ultimoCaractere = ch;
				
				if(isNumero(ch)){
					if(!temNumero){
						forca += 10;
						temNumero = true;
					}
					
					forca += 1;
				} else if(isLetraMaiuscula(ch)) {
					if(!temLetraMaiuscula){
						forca += temLetraMinuscula? 15: 10;
						temLetraMaiuscula = true;
					}
					
					forca += 1;
				} else if(isLetraMinuscula(ch)){
					if(!temLetraMinuscula){
						forca += temLetraMaiuscula? 15: 10;
						temLetraMinuscula = true;
					}
					
					forca += 1;
				} else if(isCaractereEspecial(ch)){
					if(!temCaractereEspecial){
						forca += 15;
						temCaractereEspecial = true;
					}
					
					forca += 2;
				} else {
					return -1; //invalido
				}
			}
		}
		
		return forca;
	}
	
	public static char[] charValidos = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_abcdefghijklmnopqrstuvwxyz{|}~¨°ÀÁÂÃÇÈÉÊÌÍÎÒÓÔÕÙÚÛàáâãçèéêìíîòóôõùúû".toCharArray();
	
	public static boolean isNumero(char ch){
		return ch >= '0' && ch <= '9';
	}
	
	public static boolean isLetraMaiuscula(char ch){
		return (ch >= 'A' && ch <= 'Z') || ch == 'À'|| ch == 'Á'|| ch == 'Â'|| ch == 'Ã'|| ch == 'Ç'|| ch == 'È'|| ch == 'É'|| ch == 'Ê'|| ch == 'Ì'|| ch == 'Í'|| ch == 'Î'|| ch == 'Ò'|| ch == 'Ó'|| ch == 'Ô'|| ch == 'Õ'|| ch == 'Ù'|| ch == 'Ú'|| ch == 'Û';
	}
	
	public static boolean isLetraMinuscula(char ch){
		return (ch >= 'a' && ch <= 'z') || ch == 'à'|| ch == 'á'|| ch == 'â'|| ch == 'ã'|| ch == 'ç'|| ch == 'è'|| ch == 'é'|| ch == 'ê'|| ch == 'ì'|| ch == 'í'|| ch == 'î'|| ch == 'ò'|| ch == 'ó'|| ch == 'ô'|| ch == 'õ'|| ch == 'ù'|| ch == 'ú'|| ch == 'û';
	}
	
	public static boolean isCaractereEspecial(char ch){
		return ch == ' ' || ch == '!'|| ch == '"'|| ch == '#'|| ch == '$'|| ch == '%'|| ch == '&'|| ch == '\''|| ch == '('|| ch == ')'|| ch == '*'|| ch == '+'|| ch == ','|| ch == '-'|| ch == '.'|| ch == '/'|| ch == ':'|| ch == ';'|| ch == '<'|| ch == '='|| ch == '>'|| ch == '?'|| ch == '@'|| ch == '['|| ch == '\\'|| ch == ']'|| ch == '^'|| ch == '_'|| ch == '{'|| ch == '|'|| ch == '}'|| ch == '~'|| ch == '|'|| ch == '¨'|| ch == '°'|| ch == '“'|| ch == '”';
	}

	private Senha() {
	}
}
