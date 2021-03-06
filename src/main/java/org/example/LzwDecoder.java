package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LzwDecoder implements LzwDecoderI{
    private String path;
    private StringBuilder saidaString;
    private HashMap<Integer, String> dicionarioDecode;
    private FileLzw fileLzw;
    private List<Integer> listadecode;
    private final int NBITS;
    private final int TAMDICIONARIO;


    public LzwDecoder(String caminho, int n){
        NBITS = n;
        TAMDICIONARIO = (int) Math.pow(2,NBITS);
        dicionarioDecode = new HashMap<>(TAMDICIONARIO);
        listadecode = new ArrayList<>();
        saidaString = new StringBuilder();
        fileLzw = new FileLzw("arqDecodificado.txt");
        path = caminho;
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

        DataInputStream entrada = null;
        try {
            entrada = (new DataInputStream(new FileInputStream(this.path)));

            int value = 0;
            int tamanho = entrada.available()  / 4;// divide por 4 porque o int tem 4 bytes

            for(int i = 0 ; i < tamanho; i++){
                value = entrada.readInt();
                listadecode.add(value);
                System.out.println(value);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void printa(){
        System.out.println("Printando indices descodificados");
        for(int aux: listadecode)
            System.out.println(aux);
    }
}
