package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LzwEncoderNovo implements LzwEncoderI {

    private StringBuilder saidaString;
    private List<Character> array;
    private HashMap<String, Integer> dicionarioCode;
    private List<Integer> saida;
    private FileLzw fileLzw;
    private final int NBITS = 10;
    private final int TAMDICIONARIO = (int) Math.pow(2,NBITS); // 2⁹

    public LzwEncoderNovo(List<Character> arrayc){
        dicionarioCode = new HashMap<>(TAMDICIONARIO);
        saida = new ArrayList<>();
        saidaString = new StringBuilder();
        array = arrayc;
        fileLzw = new FileLzw();
        initDicionario();
    }

    private void initDicionario(){
        for(int i = 0; i < 256; i++){
            dicionarioCode.put(""+(char)i, i);
        }
    }

    private char[] converteArray(List<Character> list){
        char[] myCharArray = new char[list.size()];
        for(int i = 0; i < list.size(); i++) {
            myCharArray[i] = list.get(i);
        }
        return myCharArray;
    }

    @Override
    public void compress() throws IOException {
        System.out.println("/------Codificando-----/");
        int code = 256;
        String p ="";
        String c ="";
        p += array.get(0);
        char [] arraychar = converteArray(array);
        System.out.println("/------Percorrendo array-----/");
        for(int i = 0; i < arraychar.length; i++){

            System.out.println("Código : "+code);
            if(i != arraychar.length - 1){
                c += arraychar[i + 1];
            }
            if(dicionarioCode.containsKey(p+c)){
                System.out.println("Valor "+(p+c)+" esta no dicionario");
                p = p + c;
                System.out.println("P "+(p));
            }else {
                if(dicionarioCode.size() == TAMDICIONARIO) {
                    fileLzw.addBits(dicionarioCode.get(p), NBITS);
                    p = "" + c;
                    System.out.println("P "+(p));
                }else {
                    fileLzw.addBits(dicionarioCode.get(p), NBITS);
                    saida.add(dicionarioCode.get(p));
                    dicionarioCode.put(p+c,code);
                    System.out.println("add "+(p+c)+"no dicionario");
                    code++;
                    p = "" + c;
                    System.out.println("P "+(p));
                }
            }
            c = "";

        }
        System.out.println("P "+(p));
        saida.add(dicionarioCode.get(p));
        fileLzw.addBits(dicionarioCode.get(p), NBITS);
        fileLzw.closeFile();
    }

}
