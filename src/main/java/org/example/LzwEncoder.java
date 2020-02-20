package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LzwEncoder implements LzwEncoderI{
    private StringBuilder saidaString;
    private List<Character> array;
    private HashMap<String, Integer> dicionarioCode;
    private List<Integer> saida;
    private FileLzw fileLzw;
    private final int NBITS = 10;
    private final int TAMDICIONARIO = (int) Math.pow(2,NBITS); // 2⁹

    public LzwEncoder(List<Character> arrayc){
        dicionarioCode = new HashMap<>(TAMDICIONARIO);
        saida = new ArrayList<>();
        saidaString = new StringBuilder();
        array = arrayc;
        fileLzw = new FileLzw();
        initDicionario();
    }

//    public void compress(List<Character> array) throws IOException {
//        System.out.println("/------Codificando-----/");
//        int code = 256;
//        String p ="";
//        String c ="";
//        p += array.get(0);
//        char [] arraychar = converteArray(array);
//        System.out.println("/------Percorrendo array-----/");
//        for(int i = 0; i < arraychar.length; i++){
//
//            System.out.println("Código : "+code);
//            if(code == 290){
//                System.out.println("Break Point");
//                System.out.println("Valor de p "+p);
//                System.out.println("Valor de p + c "+(p+c));
//                System.out.println("Tamnho dicionario "+dicionarioCode.size());
//            }
////            System.out.println("Faltam "+(arraychar.length - i)+" interações");
//            if(i != arraychar.length - 1){
//                c += arraychar[i + 1];
//            }
//            if(dicionarioCode.containsKey(p+c)){
//                System.out.println("Valor "+(p+c)+" esta no dicionario");
//                p = p + c;
//                System.out.println("P "+(p));
//            }else {
//                if(dicionarioCode.size() == TAMDICIONARIO) {
////                    System.out.println("Dicionário cheio");
////                    fileLzw.addBits(dicionarioCode.get(p), NBITS);
//                    fileLzw.salvaBytesCompress(dicionarioCode.get(p));
//                    p = "" + c;
//                }else {
////                    System.out.println(dicionarioCode.get(p));
//                    System.out.println("add dicionario");
//                    System.out.println(dicionarioCode.get(p));
////                    fileLzw.addBits(dicionarioCode.get(p), NBITS);
////                    fileLzw.salvaBytes(dicionarioCode.get(p), NBITS);
//                    fileLzw.salvaBytesCompress(dicionarioCode.get(p));
//                    saida.add(dicionarioCode.get(p));
//                    dicionarioCode.put(p+c,code);
//                    code++;
//                    p = "" + c;
//                }
//            }
//            c = "";
//
//        }
//        saida.add(dicionarioCode.get(p));
////        fileLzw.addBits(dicionarioCode.get(p), NBITS);
//        fileLzw.salvaBytesCompress(dicionarioCode.get(p));
//
////                fileLzw.salvaFim();
//
//        fileLzw.closeFile();
////        fileLzw.addBits(dicionarioCode.get(p), NBITS);
//    }

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
            if(code == 290){
                System.out.println("Break Point");
                System.out.println("Valor de p "+p);
                System.out.println("Valor de p + c "+(p+c));
                System.out.println("Tamnho dicionario "+dicionarioCode.size());
            }
//            System.out.println("Faltam "+(arraychar.length - i)+" interações");
            if(i != arraychar.length - 1){
                c += arraychar[i + 1];
            }
            if(dicionarioCode.containsKey(p+c)){
                System.out.println("Valor "+(p+c)+" esta no dicionario");
                p = p + c;
                System.out.println("P "+(p));
            }else {
                if(dicionarioCode.size() == TAMDICIONARIO) {
//                    System.out.println("Dicionário cheio");
//                    fileLzw.addBits(dicionarioCode.get(p), NBITS);
                    fileLzw.salvaBytesCompress(dicionarioCode.get(p));
                    p = "" + c;
                }else {
//                    System.out.println(dicionarioCode.get(p));
                    System.out.println("add dicionario");
                    System.out.println(dicionarioCode.get(p));
//                    fileLzw.addBits(dicionarioCode.get(p), NBITS);
//                    fileLzw.salvaBytes(dicionarioCode.get(p), NBITS);
                    fileLzw.salvaBytesCompress(dicionarioCode.get(p));
                    saida.add(dicionarioCode.get(p));
                    dicionarioCode.put(p+c,code);
                    code++;
                    p = "" + c;
                }
            }
            c = "";

        }
        saida.add(dicionarioCode.get(p));
//        fileLzw.addBits(dicionarioCode.get(p), NBITS);
        fileLzw.salvaBytesCompress(dicionarioCode.get(p));

//                fileLzw.salvaFim();

        fileLzw.closeFile();
//        fileLzw.addBits(dicionarioCode.get(p), NBITS);
    }

}
