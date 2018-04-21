package project.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

    }

