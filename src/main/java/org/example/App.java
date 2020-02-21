package org.example;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Compressor LZW
 *
 */
public class App 
{
    private static final int TAMINDICE = 9;
    private static final String video = "sample.mp4";
    private static final String textoTest = "test.txt";
    private static final String textProfessor = "corpus16MB.txt";

    public static void main( String[] args ){
        byte [] bytes = new byte[1];
        List<Character> listchar = new ArrayList<>();

        if(args.length < 3){
            System.out.println("-c compressão");
            System.out.println("-d descompressão");
            System.out.println("nbits quantidade de bits");
            System.out.println("Exemplo de uso compressão: java -jar lzw-1.0-SNAPSHOT.jar -c file.txt nbits");
            System.out.println("Exemplo de uso descompressão: java -jar lzw-1.0-SNAPSHOT.jar -d saida.lzw nbits");

        }else {
            String param1 = args[0];
            String param2 = args[1];
            String param3 = args[2];

            for(int i = 0; i < args.length; i++){
                if(args[i] == null){
                    System.out.println("Parâmetros inválidos");
                    System.out.println("-c compressão");
                    System.out.println("-d descompressão");
                    System.out.println("nbits quantidade de bits");
                    System.out.println("Exemplo de uso compressão: java -jar lzw-1.0-SNAPSHOT.jar -c file.txt nbits");
                    System.out.println("Exemplo de uso descompressão: java -jar lzw-1.0-SNAPSHOT.jar -d saida.lzw nbits");
                    return;
                }
            }

            try {
                FileInputStream file = new FileInputStream(param2);
                DataInputStream entrada = new DataInputStream(new BufferedInputStream(file));

                while (true){
                    byte valor = entrada.readByte();
                    listchar.add((char)valor);
                }

//            while( (byteslidos = file.read(bytes)) != -1) {
//                char aux = (char) bytes[0];
//                listchar.add(aux);
//            }

//            FileLzw gravar = new FileLzw();
//            gravar.addBits(20, 9);
//            gravar.addBits(30, 9);
//            gravar.addBits(80, 9);
//            gravar.addBits(200, 9);
//            gravar.salvaFim();

//            LzwDecoder lzwDecoder = new LzwDecoder("saida.lzw");
//            lzwDecoder.decode();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (EOFException e){

                switch (param1) {
                    case "-c":
                        LzwEncoderI lzw = new LzwEncoder(listchar);
                        try {
                            lzw.compress();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        break;
                    case "-d":
                        LzwDecoderI lzwDecoder = new LzwDecoder(param2, TAMINDICE);
                        try {
                            lzwDecoder.decode();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        break;
                    default:
                        System.out.println("Parâmetros inválidos");
                        System.out.println("-c compressão");
                        System.out.println("-d descompressão");
                        System.out.println("nbits quantidade de bits");
                        System.out.println("Exemplo de uso compressão: java -jar lzw-1.0-SNAPSHOT.jar -c file.txt nbits");
                        System.out.println("Exemplo de uso descompressão: java -jar lzw-1.0-SNAPSHOT.jar -d saida.lzw nbits");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        int byteslidos = 0;
//
//        byte [] bytes = new byte[1];
//        List<Character> listchar = new ArrayList<>();
//
//        try {
//            FileInputStream file = new FileInputStream(textoTest);
//            DataInputStream entrada = new DataInputStream(new BufferedInputStream(file));
//
//            while (true){
//                byte valor = entrada.readByte();
//                listchar.add((char)valor);
//            }
//
////            while( (byteslidos = file.read(bytes)) != -1) {
////                char aux = (char) bytes[0];
////                listchar.add(aux);
////            }
//
////            FileLzw gravar = new FileLzw();
////            gravar.addBits(20, 9);
////            gravar.addBits(30, 9);
////            gravar.addBits(80, 9);
////            gravar.addBits(200, 9);
////            gravar.salvaFim();
//
////            LzwDecoder lzwDecoder = new LzwDecoder("saida.lzw");
////            lzwDecoder.decode();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (EOFException e){
//            LzwEncoderI lzw = new LzwEncoder(listchar);
//            try {
//                lzw.compress();
//                LzwDecoderI lzwDecoder = new LzwDecoder("saida.lzw", TAMINDICE);
//                lzwDecoder.decode();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static String getString(ByteBuffer bbytes, int tam){
        StringBuilder buildString = new StringBuilder();
        for(int i = 0; i < tam; i++){
            buildString.append((char)bbytes.get());
        }
        return buildString.toString();
    }


}
