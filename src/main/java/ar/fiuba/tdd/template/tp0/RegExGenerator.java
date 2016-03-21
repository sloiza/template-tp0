package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegExGenerator {
   private int maxLength;

   public RegExGenerator(int maxLength) {
       this.maxLength = maxLength;
   }

    protected String generateRandomCharacter(int cantChar){
        Constants cons = new Constants();
        String cadena = "";
        for (int i = 0; i < cantChar; i++) {
            int randomInt;
            randomInt = this.generateRandomInt(Constants.MIN_ASCII, Constants.MAX_ASCII);
            String charValue = Character.toString((char) randomInt);
            cadena+=charValue;
        }
        return cadena;
    }

    protected String getCharacters(char[] conj, int cantChar){
        String cadena = "";
        for (int i = 0; i < cantChar; i++) {
            int randomInt = this.generateRandomInt(0,conj.length-1);
            char charValue = conj[randomInt];
            cadena+=charValue;
        }
        return cadena;
    }

    protected String getNCharacters(char letter, int cantChar){
        String cadena = "";
        for (int i = 0; i < cantChar; i++) {
            cadena+=letter;
        }
        return cadena;
    }
    private int generateRandomInt(int min, int max){
        Random randomGenerator = new Random();
        return randomGenerator.nextInt((max - min) + 1) + min;
    }

    private char[] resizeArray(char[] array, int length){
        char[] new_array = new char[length];
        for (int i = 0; i < length; i++){
            new_array[i] = array[i];
        }
        return new_array;
    }

    public List<String> generate(String regEx, int numberOfResults) {
        System.out.println("Regex: "+regEx);
        ArrayList<String> words;
        words = new ArrayList<String>();
        for(int i = 0; i < numberOfResults; i++ ){
            //System.out.printf("WORD: %d\n", i);
            String palabra = "";
            for (int j = 0; j < regEx.length(); j++){
                //System.out.printf("POS: %d \n", j);
                char c = regEx.charAt(j);
               // System.out.printf("Actual char: %c \n", c);

                if(c == '.'){
                    if((j+1 < regEx.length()) && this.isQuantifier(regEx.charAt(j+1))){
                        j = j+1;
                        char q = regEx.charAt(j);
                        if(q == '*'){
                            int x = this.generateRandomInt(0,this.maxLength);
                            //System.out.printf("X: %d \n", x);
                            palabra+= this.generateRandomCharacter(x);
                        }else if(q == '+'){
                            int x = this.generateRandomInt(1,this.maxLength);
                            //System.out.printf("X: %d \n", x);
                            palabra+= this.generateRandomCharacter(x);
                        }else if(q == '?') {
                            int x = this.generateRandomInt(0, 1);
                            //System.out.printf("X: %d \n", x);
                            palabra += this.generateRandomCharacter(x);
                        }else{

                        }
                    }else{
                        palabra+= this.generateRandomCharacter(1);
                    }
                }else if(c == '['){
                    j++;
                    int index = regEx.indexOf(']');
                    if(index == -1){
                       // System.out.println("\n BAD REGEX \n");
                    }else{
                        int cant = index - j;
                        //System.out.printf("\n Cant: %d \n", cant);
                        char[] conj = new char[cant];
                        int in = 0;
                        int size = cant;
                        for (int k = 0; k< cant; k++){
                           // System.out.printf("\n K: %d\n ", k);
                            char m = regEx.charAt(j);
                            //System.out.printf("\n char conj: %c\n ", m);
                            if(m == '\\'){
                                j++;
                                conj = this.resizeArray(conj, size -1);
                                size--;
                                continue;
                            }
                            conj[in] = m;
                            in++;
                            j++;
                        }
                        /*for(int p = 0; p < conj.length ; p++){
                            System.out.printf("\n conjunto: %c\n ", conj[p]);
                        }
                        System.out.printf("After: %d \n", j);
                        */
                        if((j+1 < regEx.length()) && this.isQuantifier(regEx.charAt(j+1))){
                            j = j+1;
                            char q = regEx.charAt(j);
                            if(q == '*'){
                                int x = this.generateRandomInt(0,this.maxLength);
                                //System.out.printf("X: %d \n", x);
                                palabra+= this.getCharacters(conj,x);
                            }else if(q == '+'){
                                int x = this.generateRandomInt(1,this.maxLength);
                               // System.out.printf("X: %d \n", x);
                                palabra+= this.getCharacters(conj,x);
                            }else if(q == '?') {
                                int x = this.generateRandomInt(0, 1);
                                //System.out.printf("X: %d \n", x);
                                palabra += this.getCharacters(conj,x);
                            }else{

                            }
                        }else{
                            int x = this.generateRandomInt(1, cant-1);
                            palabra+= conj[x];
                        }

                    } //end case set
                }else if( c == '\\'){
                    //System.out.printf("POS: %d \n", j);
                    if((j+1 < regEx.length()) && this.isQuantifier(regEx.charAt(j+1))){
                        j++;
                        char q = regEx.charAt(j);
                        if(q == '*'){
                            int x = this.generateRandomInt(0,this.maxLength);
                            //System.out.printf("X: %d \n", x);
                            palabra+= this.getNCharacters(c,x);
                        }else if(q == '+'){
                            int x = this.generateRandomInt(1,this.maxLength);
                            //System.out.printf("X: %d \n", x);
                            palabra+= this.getNCharacters(c,x);
                        }else if(q == '?') {
                            int x = this.generateRandomInt(0, 1);
                            //System.out.printf("X: %d \n", x);
                            palabra += this.getNCharacters(c,x);
                        }else{

                        }

                    }else{
                        //Si despues de \ no viene un identificador me fijo si hay otro caracter, lo leo
                        if(j+1 < regEx.length()){
                            //System.out.printf("POS: %d \n", j);
                            char value = regEx.charAt(j+1);
                           //System.out.printf("read: %c \n", value);
                            j++;
                            //System.out.printf("POS: %d \n", j);
                            //Si lo que continua es un cuantificdor
                            if((j+1 < regEx.length()) && this.isQuantifier(regEx.charAt(j+1))){
                                j++;
                                char q = regEx.charAt(j);
                                if(q == '*'){
                                    int x = this.generateRandomInt(0,this.maxLength);
                                    //System.out.printf("X: %d \n", x);
                                    palabra+= this.getNCharacters(value,x);
                                }else if(q == '+'){
                                    int x = this.generateRandomInt(1,this.maxLength);
                                    //System.out.printf("X: %d \n", x);
                                    palabra+= this.getNCharacters(value,x);
                                }else if(q == '?') {
                                    int x = this.generateRandomInt(0, 1);
                                    //System.out.printf("X: %d \n", x);
                                    palabra += this.getNCharacters(value,x);
                                }else{

                                }

                            }else{
                                palabra+=value;
                                //System.out.println(palabra);
                            }
                        }else{
                            palabra+=c;
                        }
                    }
                }else{
                    if((j+1 < regEx.length()) && this.isQuantifier(regEx.charAt(j+1))){
                        j++;
                        char q = regEx.charAt(j);
                        if(q == '*'){
                            int x = this.generateRandomInt(0,this.maxLength);
                            //System.out.printf("X: %d \n", x);
                            palabra+= this.getNCharacters(c,x);
                        }else if(q == '+'){
                            int x = this.generateRandomInt(1,this.maxLength);
                            //System.out.printf("X: %d \n", x);
                            palabra+= this.getNCharacters(c,x);
                        }else if(q == '?') {
                            int x = this.generateRandomInt(0, 1);
                            //System.out.printf("X: %d \n", x);
                            palabra += this.getNCharacters(c,x);
                        }else{

                        }

                    }else{
                        palabra+=c;
                    }
                }
            }
            //System.out.println(palabra);
            words.add(palabra);
        }
       /*for (int j = 0; j < words.size(); j++) {
           System.out.printf("\n Palabra %d \n", j);
            System.out.println(words.get(j));
        }*/

        return words;
    }

    private boolean isQuantifier(char c){
        if(c != 0){
            return (c == '*') || (c == '+') || (c == '?');
        }else return false;
    }

}