package es.angelluis.assembler;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by angelluis on 24/05/15.
 *
 * Clase usada para parsear el fichero ensamblador fuente
 *
 * @author Ángel Luis Perales Gómez
 */
public class Parser {

    public String currentCommand;
    private BufferedReader br;
    public final static int TYPE_A = 0;
    public final static int TYPE_C = 1;
    public final static int TYPE_LABEL = 2;
    public final static int TYPE_ERROR = 0xFF;

    public Parser(String file) {
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * Procesa la siguiente instrucción disponible.
     *
     * Este método es el encargado también de eliminar caracteres innecesarios del fichero, como por ejemplo
     * los espacios en blanco o los comentarios dentro del código fuente.
     *
     * @return Devuelve true si hay siguente instrucción y false en caso contrario.
     */
    public boolean process(){
        try {
            currentCommand = br.readLine();
            if (currentCommand == null){
                return false;
            }
            currentCommand = currentCommand.replaceAll("\\/\\/.*", "").trim();
            while (currentCommand.trim().equals("")){
                currentCommand = br.readLine();
                if (currentCommand == null){
                    return false;
                }
                currentCommand = currentCommand.replaceAll("\\/\\/.*", "").trim();
            }
            if (currentCommand == null) {
                return false;
            }else{
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /***
     * Devuelve el tipo de comando actual.
     *
     * El tipo puede ser:
     * <ul>
     *     <li>Instrucción de tipo A</li>
     *     <li>Instrucción de tipo C</li>
     *     <li>Etiqueta</li>
     * </ul>
     *
     * @return Devuelve el tipo de instrucción actual.
     */
    public int commandType(){
        if (currentCommand.startsWith("@")){
            return TYPE_A;
        }else if(currentCommand.contains("=") || currentCommand.contains(";")){
            return TYPE_C;
        }else if (currentCommand.startsWith("(")) {
            return TYPE_LABEL;
        }else{
            return TYPE_ERROR;
        }
    }


    /***
     * Este método parsea el campo dest de las instrucciones tipo C.
     *
     * @return Devuelve el campo dest de las instrucciones tipo C.
     */
    public String dest(){
        String result = new String();
        Pattern pattern = Pattern.compile("([A-Z]{1,3})(?==)");
        Matcher matcher = pattern.matcher(currentCommand);
        if (matcher.find()){
            result = matcher.group(1);
        }
        return result;
    }

    /***
     * Este método parsea el campo comp de las instrucciones de tipo C.
     *
     * @return Develve el campo comp de las instrucciones de tipo C.
     */
    public String comp(){
        Pattern pattern = Pattern.compile("(=)([^\\r\\n]*)");
        Matcher matcher = pattern.matcher(currentCommand);
        String result = new String();
        if (matcher.find()){
            result = matcher.group(2);
        }else{
            Pattern pattern1 = Pattern.compile("([^\\r\\n]*)(;)");
            Matcher matcher1 = pattern1.matcher(currentCommand);
            if (matcher1.find()) {
                result = matcher1.group(1);
            }
        }
        return result;
    }

    /***
     * Este método parsea el campo jump de las instrucciones de tipo C.
     *
     * @return Develve el campo jump de las instrucciones de tipo C.
     */
    public String jump(){
        Pattern pattern = Pattern.compile("(;)([^\\r\\n]*)");
        Matcher matcher = pattern.matcher(currentCommand);
        String result = new String();
        if (matcher.find()){
            result = matcher.group(2);
        }

        return result;
    }

    /***
     * Este método parsea los símbolos del fichero fuente.
     *
     * @return Devuelve el símbolo parseado.
     */
    public String symbol() {
        Pattern pattern = Pattern.compile("(@)([^\\r\\n]*)");
        Matcher matcher = pattern.matcher(currentCommand);
        String result = new String();
        if (matcher.find()) {
            result = matcher.group(2);
        } else {
            pattern = Pattern.compile("(\\()([^\\r\\n]*)(\\))");
            matcher = pattern.matcher(currentCommand);
            if (matcher.find()) {
                result = matcher.group(2);
            }

        }
        return result;

    }
}
