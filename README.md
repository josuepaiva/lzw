# Lzw

LZW is a data compression algorithm, derived from the LZ78 algorithm, based on the location and registration of the patterns of a structure.

## Installation

Use the [maven](http://maven.apache.org/download.cgi) for generate a jar file.

```bash
mvn package
```

## Usage

```cmd
-c compressão
-d descompressão
nbits quantidade de bits
Exemplo de uso compressão: java -jar lzw-1.0-SNAPSHOT.jar -c file.txt nbits
Exemplo de uso descompressão: java -jar lzw-1.0-SNAPSHOT.jar -d saida.lzw nbits
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
