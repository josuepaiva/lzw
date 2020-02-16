package org.example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileLzw {
    private FileOutputStream saida;
    private final int SIZE = 6;
    private byte pos = 0;
    private char [] buffer;
    private OutputStream file;

    public FileLzw() {
        buffer = new char[SIZE];
        try {
            file =  new FileOutputStream("saida.lzw");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addBits(int value, int qbits) throws IOException {
        System.out.println("/------Salvando no arquivo array-----/");
        int bytes = pos / 8; // posicao do proximo byte com bits disponiveis
        int shift = pos % 8; // quant de bits utilizados
        int disp = 8 - shift; // quant de bits disponiveis
        int mask = 0;
        int falta = qbits - disp; // bits que faltam para o proximo byte
        int falta2 = 0; // bits que faltan para o segundo proximo byte
        int i;

        if (falta > 8) {
            falta2 = falta - 8;
            falta = 8;
        }

        mask = 0;
        for (i = 0 ; i < disp ; i++)
            mask = mask | (1 << i);

        buffer[bytes] = (char) ((buffer[bytes] & (~mask & 0xFF)) | ((value >> falta) & mask));

        if (falta > 0) {
            mask = 0;
            for (i = 0 ; i < (8 - falta) ; i++)
                mask = mask | (1 << i);
            buffer[bytes + 1] = (char) ((buffer[bytes + 1] & mask) | ( (value << (8 - falta)) & (~mask & 0xFF)));
            //printf("mask2=0x%02X (~0x%02X)\n", mask, (~mask & 0xFF));
        }

        if (falta2 > 0) {
            mask = 0;
            for (i = 0 ; i < (8 - falta2) ; i++)
                mask = mask | (1 << i);
            buffer[bytes + 2] = (char) ((buffer[bytes + 2] & mask) | ((value << (8 - falta2)) & (~mask & 0xFF)));
            //printf("mask3=0x%02X (~0x%02X)\n", mask, (~mask & 0xFF));
        }

        pos += qbits;

        // Adicionado para salvar os bytes as poucos.
        bytes = pos / 8; // posicao do proximo byte com bits disponiveis

        String aux = new String(buffer);

        if (bytes >= SIZE - 3) {
            file.write(aux.getBytes(), 1, bytes);
            file.flush();
            //printf("salvei %d bytes\n", byte);
            pos -= bytes * 8;
            shift = pos % 8; // quant de bits utilizados
            for (i = 0 ; i < (8-shift) ; i++)
                mask = mask | (1 << i);
            buffer[0] = (char) (buffer[bytes] & ~mask);
        }
    }

    public void salvaFim() throws IOException {
        int bytes = pos / 8; // posicao do proximo byte com bits disponiveis
        int shift = pos % 8;
        String aux = new String(buffer);

        if (shift > 0)
            bytes++;
        if (bytes > 0) {
            file.write(aux.getBytes(), 1, bytes);
            file.flush();
            //printf("salvei fim %d bytes\n", byte);
        }

        file.close();
    }
}
