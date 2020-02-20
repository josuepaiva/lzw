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
        int byteslidos = 0;

        byte [] bytes = new byte[1];
        List<Character> listchar = new ArrayList<>();

        try {
            FileInputStream file = new FileInputStream(textoTest);
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
            LzwEncoderI lzw = new LzwEncoder(listchar);
            try {
                lzw.compress();
                LzwDecoderI lzwDecoder = new LzwDecoder("saida.lzw", TAMINDICE);
                lzwDecoder.decode();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(ByteBuffer bbytes, int tam){
        StringBuilder buildString = new StringBuilder();
        for(int i = 0; i < tam; i++){
            buildString.append((char)bbytes.get());
        }
        return buildString.toString();
    }


}
