package es.angelluis.assembler;

/**
 * Created by angelluis on 28/05/15.
 */
public class Translator {

    public static String cInstruction(String cmp, String dest, String jmp){
        return "111"+cmp(cmp)+dest(dest) + jmp(jmp);
    }

    public static String dest(String dest){
        String result;

        if (dest.equals("M")){
            result = "001";
        }else if (dest.equals("D")){
            result = "010";
        }else if (dest.equals("MD")){
            result = "011";
        }else if (dest.equals("A")){
            result = "100";
        }else if (dest.equals("AM")){
            result = "101";
        }else if (dest.equals("AD")){
            result = "110";
        }else if (dest.equals("AMD")){
            result = "111";
        }else {
            result = "000";
        }
        return result;
    }

    public static String jmp(String jmp){
        String result;

        if (jmp.equals("JGT")){
            result = "001";
        }else if (jmp.equals("JEQ")){
            result = "010";
        }else if (jmp.equals("JGE")){
            result = "011";
        }else if (jmp.equals("JLT")){
            result = "100";
        }else if (jmp.equals("JNE")){
            result = "101";
        }else if (jmp.equals("JLE")){
            result = "110";
        }else if (jmp.equals("JMP")){
            result = "111";
        }else {
            result = "000";
        }
        return result;
    }

    public static String cmp(String cmp){
        String result;

        if (cmp.equals("0")){
            result = "0101010";
        }else if (cmp.equals("1")){
            result = "0111111";
        }else if (cmp.equals("-1")){
            result = "0111010";
        }else if (cmp.equals("D")){
            result = "0001100";
        }else if (cmp.equals("A")){
            result = "0110000";
        }else if (cmp.equals("!D")){
            result = "0001101";
        }else if (cmp.equals("!A")){
            result = "0110001";
        }else if (cmp.equals("-D")){
            result = "0001111";
        }else if (cmp.equals("-A")){
            result = "0110011";
        }else if (cmp.equals("D+1")){
            result = "0011111";
        }else if (cmp.equals("A+1")){
            result = "0110111";
        }else if (cmp.equals("D-1")){
            result = "0001110";
        }else if (cmp.equals("A-1")){
            result = "0110010";
        }else if (cmp.equals("D+A")){
            result = "0000010";
        }else if (cmp.equals("D-A")){
            result = "0010011";
        }else if (cmp.equals("A-D")){
            result = "0000111";
        }else if (cmp.equals("D&A")){
            result = "0000000";
        }else if (cmp.equals("D|A")){
            result = "0010101";
        }else if (cmp.equals("M")){
            result = "1110000";
        }else if (cmp.equals("!M")){
            result = "1110001";
        }else if (cmp.equals("-M")){
            result = "1110011";
        }else if (cmp.equals("M+1")){
            result = "1110111";
        }else if (cmp.equals("M-1")){
            result = "1110010";
        }else if (cmp.equals("D+M")){
            result = "1000010";
        }else if (cmp.equals("D-M")){
            result = "1010011";
        }else if (cmp.equals("M-D")){
            result = "1000111";
        }else if (cmp.equals("D&M")){
            result = "1000000";
        }else if (cmp.equals("D|M")){
            result = "1010101";
        }else {
            result = "000";
        }
        return result;
    }

    public static String aInstruction(String value){
        return Integer.toBinaryString(0x10000 | Integer.parseInt(value)).substring(1);

    }
}
