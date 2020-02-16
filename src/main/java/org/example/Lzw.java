package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lzw {
    private String dados;
    private StringBuilder saidaString;
    private HashMap<String, Integer> dicionarioCode;
    private HashMap<Integer, String> dicionarioDecode;
    private List<Integer> saida;

    public Lzw(){
        dicionarioCode = new HashMap<>();
        dicionarioDecode = new HashMap<>();
        saida = new ArrayList<>();
        saidaString = new StringBuilder();
        initDicionario();
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
                saida.add(dicionarioCode.get(p));
                dicionarioCode.put(p+c,code);
                code++;
                p = c;
            }
            c = "";
        }
        saida.add(dicionarioCode.get(p));
        mostraSaida();
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
}
