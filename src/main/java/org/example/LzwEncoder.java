package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LzwEncoder {
    private String dados;
    private StringBuilder saidaString;
    private HashMap<String, Integer> dicionarioCode;
    private HashMap<Integer, String> dicionarioDecode;
    private List<Integer> saida;
    private FileOutputStream saidacode;
    private FileLzw fileLzw;

    private final int TAMDICIONARIO = 265;
    private final int NBITS = 9;


    public LzwEncoder(){
        dicionarioCode = new HashMap<>(TAMDICIONARIO);
        dicionarioDecode = new HashMap<>();
        saida = new ArrayList<>();
        saidaString = new StringBuilder();
        fileLzw = new FileLzw();
        initDicionario();
    }

    public void compress(List<Character> array) throws IOException {
        System.out.println("/------Codificando-----/");
        int code = 256;
        String p ="";
        String c ="";
        p += array.get(0);
        char [] arraychar = converteArray(array);
        System.out.println("/------Percorrendo array-----/");
        for(int i = 0; i < arraychar.length; i++){
            System.out.println("Faltam "+(arraychar.length - i)+" interações");
            if(i != arraychar.length - 1){
                c += arraychar[i + 1];
            }
            if(dicionarioCode.containsKey(p+c)){
                System.out.println("Valor "+(p+c)+" esta no dicionario");
                p = p + c;
                System.out.println("P "+(p));
            }else {
                if(dicionarioCode.size() == TAMDICIONARIO) {
                    p = p + c;
                }else {
                    fileLzw.addBits(dicionarioCode.get(p), NBITS);
                    saida.add(dicionarioCode.get(p));
                    dicionarioCode.put(p+c,code);
                    code++;
                    p = c;
                }
            }
            c = "";

        }
        saida.add(dicionarioCode.get(p));
//        fileLzw.addBits(dicionarioCode.get(p), NBITS);
        fileLzw.salvaFim();
//        mostraSaida();
    }

    public void compress(String dados){
        this.dados = dados;
        System.out.println(dados);
        System.out.println("/------Codificando-----/");
        int code = 256;
        String p ="";
        String c ="";
        p = dados.substring(0,1);
        char [] arraychar = dados.toCharArray();

        for(int i = 0; i < arraychar.length; i++){
            if(i != arraychar.length - 1){
                c += arraychar[i + 1];
            }
            if(dicionarioCode.containsKey(p+c)){
                p = p + c;
            }else {
                if(dicionarioCode.size() == TAMDICIONARIO) {
                    p = p + c;
                }else {
                    saida.add(dicionarioCode.get(p));

                    dicionarioCode.put(p+c,code);
                    code++;
                    p = c;
                }
            }
            c = "";

        }
        System.out.println("/------Terminou de ler o array-----/");
        saida.add(dicionarioCode.get(p));
//        mostraSaida();
    }

    private void initDicionario(){
        for(int i = 0; i <= 255; i++){
            dicionarioCode.put(""+(char)i, i);
        }

        for(int i = 0; i <= 255; i++){
            dicionarioDecode.put( i, ""+(char)i);
        }
    }

    public void decode(){
        System.out.println("/------Decodificando-----/");
        if(saida.size() == 0){
            return;
        }

        int antigo = saida.get(0), n;
        String s = dicionarioDecode.get(antigo);
        String c = "";
        c = s.substring(0,1);
        System.out.print(s);
        this.saidaString.append(s);
        int contador = 256;

        for(int i = 0; i < saida.size() -1; i++){
            n = saida.get(i+1);
            if(dicionarioDecode.containsKey(n)){
                s = dicionarioDecode.get(n);
            }else {
                s = dicionarioDecode.get(antigo);
                s = s+ c;
            }
            System.out.print(s);
            this.saidaString.append(s);
            c = "";
            c+= s.substring(0,1);
            dicionarioDecode.put(contador, dicionarioDecode.get(antigo) + c);
            contador++;
            antigo = n;
        }

        if(this.dados.equals(saidaString.toString())){
            System.out.println("\n\nCodificação e decodificação ocorreram bem");
        }
    }
    private void mostraSaida(){
        for(int aux: this.saida){
            System.out.println(aux);
        }
    }

    private char[] converteArray(List<Character> list){
        char[] myCharArray = new char[list.size()];
        for(int i = 0; i < list.size(); i++) {
            myCharArray[i] = list.get(i);
        }
        return myCharArray;
    }
}
