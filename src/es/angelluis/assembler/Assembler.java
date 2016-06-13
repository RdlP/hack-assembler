package es.angelluis.assembler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by angelluis on 28/05/15.
 *
 * Clase principal del tradcutor.
 *
 * Esta clase se encarga de ir leyendo uno a uno las instrucciones, parsearlas,
 * generar el código binario en una representación String e imprimir por pantalla
 * el código binario resultante en formato hexadecimal.
 *
 */
public class Assembler {

    private String inputFile;
    //private SymbolTable symbolTable;
    private HashMap<String, Integer> symbolTable;
    private int address=16;
    private ArrayList<Integer> binaryCode;

    public Assembler(String inputFile){
        this.inputFile = inputFile;
        //symbolTable = new SymbolTable();
        initSymbolTable();
        binaryCode = new ArrayList<Integer>();
    }

    private void initSymbolTable(){
        symbolTable = new HashMap<String, Integer>();
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        for(int i=0; i<=15;i++)
        {
            symbolTable.put("R"+i,i);
        }
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
    }


    public String Assemble(){
        firstPass();
        System.out.println("======================");
        System.out.println("=Traducción a binario=");
        System.out.println("======================");
        String code = finalPass();
        System.out.println(joinCode(code));
        System.out.println("=====================");
        System.out.println("=Volcado hexadecimal=");
        System.out.println("=====================");
        printCode();
        return code;
    }

    private String joinCode(String code){
        String newCode="";
        String [] c = code.split("\n");
        int offset = 0;
        for (String b: c){
            newCode += b;
            offset++;
            if (offset == 4){
                offset = 0;
                newCode +="\n";
            }
        }
        return newCode;
    }

    private void firstPass(){
        Parser parser = new Parser(inputFile);
        int n = 0;
        while (parser.process()){
            if (parser.commandType() == Parser.TYPE_LABEL){
                symbolTable.put(parser.symbol(), n);
            }else if (parser.commandType() == Parser.TYPE_C || parser.commandType() == Parser.TYPE_A){
                n++;
            }
        }
    }

    private String aCommands(String symbol){
        String result;
        if (symbolTable.containsKey(symbol)){
            int a = symbolTable.get(symbol);
            result = Translator.aInstruction("" + a);
        }else{
            int n;
            try{
                n = Integer.parseInt(symbol);
                result = Translator.aInstruction("" + n);
            }catch (NumberFormatException e){
                symbolTable.put(symbol, address);
                result = Translator.aInstruction("" + address);
                address++;
            }
        }

        return result;

    }

    private String finalPass(){
        Parser parser = new Parser(inputFile);
        address = 16;
        String line;
        StringBuilder code = new StringBuilder();
        while(parser.process()){
            if (parser.commandType() == Parser.TYPE_C){
                line = Translator.cInstruction(parser.comp(), parser.dest(), parser.jump());
                code.append(line+ "\n");
                convertToRealBinary(line);
            }else if (parser.commandType() == Parser.TYPE_A){
                line = aCommands(parser.symbol());
                code.append(line+ "\n");
                convertToRealBinary(line);
            }
        }
        return code.toString();
    }

    private void convertToRealBinary(String strBinary){
        int dec = Integer.parseInt(strBinary, 2);
        int firstByte, secondByte;
        firstByte = ((dec << 8)&0xFF);
        secondByte = ((dec)&0xFF);
        binaryCode.add(firstByte);
        binaryCode.add(secondByte);
    }

    private void printCode(){
        int offset = 0;
        int currentColumn = 0, columns = 16;
        boolean hasRepresentation;
        ArrayList<Integer> line = new ArrayList<Integer>();
        System.out.print(String.format("0x%04X", offset)+"|\t");
        for (Integer b: binaryCode){
            System.out.print(String.format("%02X", b)+" ");
            currentColumn++;
            offset++;
            line.add(b);
            if (currentColumn == columns){
                System.out.print(" | ");
                for (Integer c: line){
                    hasRepresentation = hasGraphicRepresentation(Character.toChars(c)[0]);
                    if (hasRepresentation) {
                        System.out.print("" + Character.toChars(c)[0]);
                    }else{
                        System.out.print(".");
                    }
                }
                System.out.println();
                System.out.print(String.format("0x%04X", offset) + "|\t");
                currentColumn = 0;
                line = new ArrayList<Integer>();
            }
        }
        if (!line.isEmpty()){
            int spaces = 16*3 - line.size()*2 - (line.size()-1);
            for (int i = 0; i < spaces; i++){
                System.out.print(" ");
            }
            System.out.print("| ");
            for (Integer c: line){
                hasRepresentation = hasGraphicRepresentation(Character.toChars(c)[0]);
                if (hasRepresentation) {
                    System.out.print("" + Character.toChars(c)[0]);
                }else{
                    System.out.print(".");
                }
            }
        }
    }

    private boolean hasGraphicRepresentation(char c){
        if (c < 32){
            return false;
        }
        return true;
    }

}
