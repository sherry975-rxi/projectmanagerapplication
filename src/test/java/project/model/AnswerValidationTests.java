package project.model;

import org.junit.Test;
import project.model.sendcode.AnswerValidation;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnswerValidationTests {

    AnswerValidation answerValidation = new AnswerValidation();
    String testNumber;
    String testMail;
    String msg;



    @Test
    public void performValidationMethodTest() {

        //GIVEN A EXPECTED STRING SMELLS
        String expectedResult = "What is the name of your first pet?";
        String expectedResult2 = "What elementary school did you attend?";
        String expectedResult3 = "Where did you go for your honeymoon?";
        String expectedResult4 = "";

        //WHEN the answerValidation controller executed the perfomValidationMethod, it returns a string with the result;

        String result1 = answerValidation.performValidationMethod(testNumber, testMail, "1", msg);
        String result2 = answerValidation.performValidationMethod(testNumber, testMail, "2", msg);
        String result3 = answerValidation.performValidationMethod(testNumber, testMail, "3", msg);
        String result4 = answerValidation.performValidationMethod(testNumber, testMail, "4", msg);

        //THEN verifies if both results are the same
        assertEquals(result1, expectedResult);
        assertEquals(result2, expectedResult2);
        assertEquals(result3, expectedResult3);
        assertEquals(result4, expectedResult4);


    }


    @Test
    public void questionDecoderTest(){

        //GIVEN A EXPECTED STRING SMELLS
        String expectedResult = "What is the name of your first pet?";
        String expectedResult2 = "What elementary school did you attend?";
        String expectedResult3 = "Where did you go for your honeymoon?";
        String expectedResult4 = "";

        //WHEN the answerValidation controller executed the perfomValidationMethod, it returns a string with the result;

        String result1 = answerValidation.questionDecoder("1");
        String result2 = answerValidation.questionDecoder("2");
        String result3 = answerValidation.questionDecoder("3");
        String result4 = answerValidation.questionDecoder("4");

        //THEN verifies if both results are the same
        assertEquals(result1, expectedResult);
        assertEquals(result2, expectedResult2);
        assertEquals(result3, expectedResult3);
        assertEquals(result4, expectedResult4);




    }



    @Test
    public void checkRightAnswerTest(){

        //GIVEN
        String answerOne = "AnswerOne";
        String answertwo = "AnswerOne";
        String answerThree = "AnswerThree";


        //THEN
    assertTrue(answerValidation.checkRightAnswer(answerOne,answertwo));
    assertFalse(answerValidation.checkRightAnswer(answerOne,answerThree));

    }
}
