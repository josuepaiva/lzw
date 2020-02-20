package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LzwDecoderNovo implements LzwDecoderI {
    private String path;
    private StringBuilder saidaString;
    private HashMap<Integer, String> dicionarioDecode;
    private FileLzw fileLzw;
    private List<Integer> listadecode;
    private final int NBITS;
    private final int TAMDICIONARIO;


    public LzwDecoderNovo(String caminho, int n){
        listadecode = new ArrayList<>();
        saidaString = new StringBuilder();
        fileLzw = new FileLzw("arqDecodificado.txt");
        path = caminho;
        NBITS = n;
        TAMDICIONARIO = (int) Math.pow(2,NBITS);
        dicionarioDecode = new HashMap<>(TAMDICIONARIO);
        initDicionario();
    }

    private void initDicionario(){
        for(int i = 0; i <= 255; i++){
            dicionarioDecode.put( i, ""+(char)i);
        }
    }

    public void decode() throws IOException {
        preparaArray();
        printa();
        System.out.println("/------Decodificando-----/");
        if(listadecode.size() == 0){
            return;
        }

        int antigo = listadecode.get(0), n;
        String s = dicionarioDecode.get(antigo);
        String c = "";
        c = s.substring(0,1);

        this.saidaString.append(s);

        fileLzw.salvaBytes(s);

        int contador = 256;

        for(int i = 0; i < this.listadecode.size() -1; i++){
            n = this.listadecode.get(i+1);

            if(dicionarioDecode.containsKey(n)){
                s = dicionarioDecode.get(n);
            }else {
                s = dicionarioDecode.get(antigo);
                s = s+ c;
            }

            this.saidaString.append(s);
            fileLzw.salvaBytes(s);
            c = "";
            c+= s.substring(0,1);
            dicionarioDecode.put(contador, dicionarioDecode.get(antigo) + c);
            contador++;
            antigo = n;
        }
        fileLzw.closeFile();
    }

    public void preparaArray() {
        int resto = 0;
        int result = 0;
        byte byte1 = 0;
        byte byte2 = 0;
        byte byte3 = 0;

        try( DataInputStream entrada = new DataInputStream(new BufferedInputStream(new FileInputStream(this.path)))){

            byte1 = entrada.readByte();
            byte2 = entrada.readByte();

            result = (result | byte1);
            result = result << NBITS - 8 | byte2 >> ((NBITS - 8) - 8) * -1;
            listadecode.add(result);
            resto = byte2 << NBITS - 8;

            while (true){
                byte3 = entrada.readByte();

                resto = resto << NBITS - 8;
                result = result << NBITS - 8 | byte3 >> ((NBITS - 8) - 8) * -1;
                listadecode.add(result);
                resto = byte3 << NBITS - 8;
            }

        } catch (EOFException e){
            result = byte3 << NBITS - 8;
            listadecode.add(result);
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void printa(){
        System.out.println("Printando indices descodificados");
        for(int aux: listadecode)
            System.out.println(aux);
    }
}
