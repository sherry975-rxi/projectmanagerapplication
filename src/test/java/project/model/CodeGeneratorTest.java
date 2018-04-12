package project.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CodeGeneratorTest {

    private CodeGenerator codeGenerator;


    @Before
    public void setup(){
        codeGenerator = new CodeGenerator();
    }

    @Test
    public void codeGeneratorTest(){

        /*
        The codeGenerator always generates a string with size 6
         */

        assertEquals(codeGenerator.generateCode().length(), 6);
    }

    @Test
    public void doesCodeGeneratedMatchTest() {

        //GIVEN
        String generatedCode = codeGenerator.generateCode();
        String anotherString = "NonMatchCode";

        //THEN returns true when strings match
        assertTrue(codeGenerator.doesCodeGeneratedMatch(generatedCode));

        //THEN returnd false when strings do not match
        assertFalse(codeGenerator.doesCodeGeneratedMatch(anotherString));


    }
    }

