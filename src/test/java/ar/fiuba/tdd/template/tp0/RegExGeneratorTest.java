package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(500);
        List<String> results = generator.generate(regEx, numberOfResults);
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results
                .stream()
                .reduce(true,
                    (acc, item) -> {
                        Matcher matcher = pattern.matcher(item);
                        return acc && matcher.find();
                    },
                    (item1, item2) -> item1 && item2);
    }

    @Test
    public void validateChar(){
        RegExGenerator generator = new RegExGenerator(500);
        int cantChars = 10;
        String cadena = generator.generateRandomCharacter(cantChars);
        assertEquals(true, cadena.length() == 10);
    }

    @Test
    public void genExample(){
        RegExGenerator generator = new RegExGenerator(50);
        List<String> words = generator.generate("..+[ab]*d?c", 2);

        assertEquals(true, words.size() == 2);

    }


    @Test
    public void testAnyCharacter() {

        assertTrue(validate(".", 1));
    }

    @Test
    public void testAnyCharacterTwice() { assertTrue(validate(".", 2));  }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 1));
    }

    @Test
    public void testLiteralDotCharacterN() {
        assertTrue(validate("\\@..", 5));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 1));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 1));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 1));
    }

    @Test
    public void testChar(){
        assertTrue(validate("\\\\p",1));
    }

    @Test
    public void testLetter(){ assertTrue(validate("a",1));}

    @Test
    public void testScapedValueInSet() { assertTrue(validate("[hij\\(]+", 1));}

    @Test
    public void testScapedValueInSet2() { assertTrue(validate("[ab\\@cj\\(]+", 1));}


}
