package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*

* Regex generator Class
*
*/
public class RegExGenerator {
   private int maxLength;

   public RegExGenerator(int maxLength) {
       this.maxLength = maxLength;
   }

    /*
    * Generates a string of cantChar characters random
    * */
    protected String generateRandomCharacter(int cantChar) {
        String cadena = "";
        for (int i = 0; i < cantChar; i++) {
            int randomInt;
            randomInt = this.generateRandomInt(Constants.MIN_ASCII, Constants.MAX_ASCII);
            String charValue = Character.toString((char) randomInt);
            cadena = cadena.concat(charValue);
        }
        return cadena;
    }
    /*
    * Generates a string of cantChar characters of a set
    * */
    protected String getCharacters(char[] conj, int cantChar) {
        String cadena = "";
        for (int i = 0; i < cantChar; i++) {
            int randomInt = this.generateRandomInt(0, conj.length - 1);
            char charValue = conj[randomInt];
            cadena = cadena.concat(Character.toString(charValue));
        }
        return cadena;
    }

    /*
    * Generates a string of cantChar characters of a given letter
    * */
    protected String getNCharacters(char letter, int cantChar) {
        String cadena = "";
        for (int i = 0; i < cantChar; i++) {
            cadena = cadena.concat(Character.toString(letter));
        }
        return cadena;
    }
    /*
    * Generates an int between min and max
    * */
    private int generateRandomInt(int min, int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt((max - min) + 1) + min;
    }
    /*
    * Resize an char array to less length
    * */
    private char[] resizeArray(char[] array, int length) {
        char[] newArray = new char[length];
        System.arraycopy(array, 0, newArray, 0, length);
        return newArray;
    }

    public List<String> generate(String regEx, int numberOfResults) {
        ArrayList<String> words;
        words = new ArrayList<String>();
        for (int i = 0; i < numberOfResults; i++) {
            String palabra = "";
            for (int j = 0; j < regEx.length(); j++) {
                char c = regEx.charAt(j);

                if (c == '.') {
                    if ((j + 1 < regEx.length()) && this.isQuantifier(regEx.charAt(j + 1))) {
                        j = j + 1;
                        char q = regEx.charAt(j);
                        int x = 0;
                        if (q == '*') {
                            x = this.generateRandomInt(0, this.maxLength);
                        }
                        else if (q == '+') {
                            x = this.generateRandomInt(1, this.maxLength);
                        }
                        else if (q == '?') {
                            x = this.generateRandomInt(0, 1);
                        }
                        palabra = palabra.concat(this.generateRandomCharacter(x));
                    }
                    else {
                        palabra = palabra.concat(this.generateRandomCharacter(1));
                    }
                }
                else if (c == '[') {
                    j++;
                    int index = regEx.indexOf(']');
                    if (index != -1) {
                        int cant = index - j;
                        char[] conj = new char[cant];
                        int in = 0;
                        int size = cant;
                        for (int k = 0; k < cant; k++) {
                            char m = regEx.charAt(j);
                            if (m == '\\') {
                                j++;
                                conj = this.resizeArray(conj, size - 1);
                                size--;
                                continue;
                            }
                            conj[in] = m;
                            in++;
                            j++;
                        }
                        if ((j + 1 < regEx.length()) && this.isQuantifier(regEx.charAt(j + 1))) {
                            j = j + 1;
                            char q = regEx.charAt(j);
                            int x = 0;
                            if (q == '*') {
                                x = this.generateRandomInt(0, this.maxLength);
                            }
                            else if (q == '+') {
                                x = this.generateRandomInt(1, this.maxLength);
                            }
                            else if (q == '?') {
                                x = this.generateRandomInt(0, 1);
                            }
                            palabra = palabra.concat(this.getCharacters(conj, x));
                        }
                        else {
                            int x = this.generateRandomInt(1, cant - 1);
                            palabra = palabra.concat(Character.toString(conj[x]));
                        }
                    }
                }
                else if (c == '\\') {
                    if ((j + 1 < regEx.length()) && this.isQuantifier(regEx.charAt(j + 1))) {
                        j++;
                        char q = regEx.charAt(j);
                        int x = 0;
                        if (q == '*') {
                            x = this.generateRandomInt(0, this.maxLength);
                        }
                        else if (q == '+') {
                            x = this.generateRandomInt(1, this.maxLength);
                        }
                        else if (q == '?') {
                            x = this.generateRandomInt(0, 1);
                        }
                        palabra = palabra.concat(this.getNCharacters(c, x));
                    }
                    else {
                       if (j + 1 < regEx.length()) {
                            char value = regEx.charAt(j + 1);
                            j++;
                            if ((j + 1 < regEx.length()) && this.isQuantifier(regEx.charAt(j + 1))) {
                                j++;
                                char q = regEx.charAt(j);
                                int x = 0;
                                if (q == '*') {
                                    x = this.generateRandomInt(0, this.maxLength);
                                }
                                else if (q == '+') {
                                    x = this.generateRandomInt(1, this.maxLength);
                                }
                                else if (q == '?') {
                                    x = this.generateRandomInt(0, 1);
                                }
                                palabra = palabra.concat(this.getNCharacters(value, x));
                            }
                            else {
                                palabra = palabra.concat(Character.toString(value));
                            }
                        }
                        else {
                            palabra = palabra.concat(Character.toString(c));
                        }
                    }
                }
                else {
                    if ((j + 1 < regEx.length()) && this.isQuantifier(regEx.charAt(j + 1))) {
                        j++;
                        int x = 0;
                        char q = regEx.charAt(j);
                        if (q == '*') {
                            x = this.generateRandomInt(0, this.maxLength);
                        }
                        else if (q == '+') {
                            x = this.generateRandomInt(1, this.maxLength);
                        }
                        else if (q == '?') {
                            x = this.generateRandomInt(0, 1);
                        }
                        palabra = palabra.concat(this.getNCharacters(c, x));

                    }
                    else {
                        palabra = palabra.concat(Character.toString(c));
                    }
                }
            }
            System.out.println(palabra);
            words.add(palabra);
        }

        return words;
    }

    private boolean isQuantifier(char c) {
        if (c != 0) {
            if ((c == '*') || (c == '+') || (c == '?')) {
                return true;
            }
        }
        else return false;
        return false;
    }

}