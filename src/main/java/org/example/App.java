package org.example;

import com.sun.javafx.binding.StringFormatter;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final int TAMINDICE = 9;

    public static void main( String[] args ){

        int BUFFER_SIZE;
        int tamanho = 0;
        int offset = 0;
        int cursor = 0;
        int byteslidos = 0;
        String nome = "";
        StringBuilder stringbuild = new StringBuilder();
        BufferedInputStream buffer;
        byte [] bytes = new byte[1];
        List<Character> listchar = new ArrayList<>();
        byte value;
        ByteBuffer bbytes;

        try {
            FileInputStream file = new FileInputStream("test.txt");
            buffer = new BufferedInputStream(file);

            while( (byteslidos = file.read(bytes)) != -1) {
                char aux = (char) bytes[0];
                listchar.add(aux);
            }

            Lzw lzw = new Lzw();
//            lzw.compress("WYS*WYGWYS*WYSWYSG");
            lzw.compress(listchar);

//            lzw.decode();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
