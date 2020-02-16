package org.example;

import com.sun.javafx.binding.StringFormatter;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final int TAMINDICE = 9;

    public static void main( String[] args ){
        int num = 121;
        long chunck;
        int BUFFER_SIZE = 4096, cursor = 0;
        byte[] bytesBuffer = new byte[BUFFER_SIZE];
        int bytes = 0;

        BufferedInputStream buffer;
        ByteBuffer bbytes;
        try {
            FileInputStream file = new FileInputStream("sample.mp4");
            buffer = new BufferedInputStream(file);

            buffer.read(bytesBuffer);
            bbytes = ByteBuffer.wrap(bytesBuffer);
            bbytes.order(ByteOrder.BIG_ENDIAN);

            Lzw lzw = new Lzw();
            lzw.compress("WYS*WYGWYS*WYSWYSG");
            lzw.decode();
//            while (( bytes = buffer.read(bytesBuffer)) > 0) {
//                bbytes = ByteBuffer.wrap(bytesBuffer);
//                for(int i=0; i<bytes; i++) {
//
//                    switch (TAMINDICE){
//                        case 9:
//                            short v = bbytes.getShort();
//
//                            break;
//                        case 10:
//                            break;
//                        case 11:
//                            break;
//                        case 12:
//                            break;
//                        case 13:
//                            break;
//                        case 14:
//                            break;
//                        case 15:
//                            break;
//                        case 16:
//                            break;
//                        default:
//                            System.out.println("Deu errado aqui");
//
//
//                    }
//
//                }
//            }



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
