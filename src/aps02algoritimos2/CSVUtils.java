package aps02algoritimos2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVUtils {

    private static final char SEPARADOR = ',';
    private static final char ASPAS = '"';

    public static void main(String[] args) throws Exception {

        String csvArquivo = "C:\\Users\\jonathan.antunes\\Desktop\\participantes.csv";

        Scanner scanner = new Scanner(new File(csvArquivo));
        while (scanner.hasNext()) {
            List<String> linha = parseLine(scanner.nextLine());
            System.out.println("[Nome= " + linha.get(0) + ", Email= " + linha.get(1) + "]");
        }
        scanner.close();

    }

    public static List<String> parseLine(String csvLinha) {
        return parseLine(csvLinha, SEPARADOR, ASPAS);
    }

    public static List<String> parseLine(String csvLinha, char separadores) {
        return parseLine(csvLinha, separadores, ASPAS);
    }

    public static List<String> parseLine(String csvLinha, char separadores, char aspasCustomizadas) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (csvLinha == null && csvLinha.isEmpty()) {
            return result;
        }

        if (aspasCustomizadas == ' ') {
        	aspasCustomizadas = ASPAS;
        }

        if (separadores == ' ') {
            separadores = SEPARADOR;
        }

        StringBuffer valorAtual = new StringBuffer();
        boolean emAspas = false;
        boolean comecaColetarChar = false;
        boolean duasAspasNaColuna = false;

        char[] chars = csvLinha.toCharArray();

        for (char ch : chars) {

            if (emAspas) {
            	comecaColetarChar = true;
            	
                if (ch == aspasCustomizadas) {
                	emAspas = false;
                	duasAspasNaColuna = false;
                	
                } else {
             
                    if (ch == '\"') {
                    	
                        if (!duasAspasNaColuna) {
                        	valorAtual.append(ch);
                            duasAspasNaColuna = true;
                        }
                        
                    } else {
                    	
                    	valorAtual.append(ch);
                    }

                }
                
            } else {
            	
                if (ch == aspasCustomizadas) {
                	emAspas = true;
                	
                    if (chars[0] != '"' && aspasCustomizadas == '\"') {
                    	valorAtual.append('"');
                    }
                    
                    if (comecaColetarChar) {
                    	valorAtual.append('"');
                    }
                    
                } else if (ch == separadores) {
                    result.add(valorAtual.toString());
                    valorAtual = new StringBuffer();
                    comecaColetarChar = false;
                    
                } else if (ch == '\r') {
                    continue;
                    
                } else if (ch == '\n') {
                    break;
                    
                } else {
                	valorAtual.append(ch);
                }
            }
        }
        
        result.add(valorAtual.toString());
        
        return result;
    }

}
