package es.angelluis.assembler;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String OS_VERSION = System.getProperty("os.version").toLowerCase();

    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Uso: main src dst\nAl programa se le deben proporcionar 2 argumentos, el primer argumento es la ruta del archivo fuente y el segundo argumento es la ruta donde se debe generar el archivo de salida");
            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];


        System.out.println("Traductor de ensamblador Hack a lenguaje máquina v1.0. Ángel Luis Perales Gómez. 2015");
        System.out.println("Corriendo sobre " + OS + " " + OS_VERSION);
        System.out.println("Traduciendo: " + inputPath);

        Assembler assembler = new Assembler(inputPath);
        String code = assembler.Assemble();



        PrintWriter out;
        try {
            out = new PrintWriter(outputPath);
            out.print(code);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




    }
}
