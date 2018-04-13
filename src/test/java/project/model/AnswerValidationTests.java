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

        //GIVEN
        String expectedResult = answerValidation.questionDecoder("1");
        String expectedResult2 = answerValidation.questionDecoder("2");
        String expectedResult3 = answerValidation.questionDecoder("3");
        String expectedResult4 = answerValidation.questionDecoder("");


        //THEN
        assertEquals(answerValidation.performValidationMethod(testNumber, testMail, "1", msg), expectedResult);
        assertEquals(answerValidation.performValidationMethod(testNumber, testMail, "2", msg), expectedResult2);
        assertEquals(answerValidation.performValidationMethod(testNumber, testMail, "3", msg), expectedResult3);
        assertEquals(answerValidation.performValidationMethod(testNumber, testMail, "4", msg), expectedResult4);


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
