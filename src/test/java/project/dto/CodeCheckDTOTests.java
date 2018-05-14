package project.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CodeCheckDTOTests {

    @Test
    public void getAndSetTest(){
        CodeCheckDTO code = new CodeCheckDTO();

        code.setCodeToCheck("12345");

        assertEquals("12345", code.getCodeToCheck()) ;
    }
}
