package project.model;

import java.util.Random;

public class CodeGenerator {

    private String generatedCode;


    public static String generateCode() {

        Random rand = new Random();
        Integer randomNumber = 100000 + rand.nextInt(900000);
        String generatedCode = randomNumber.toString();

        return generatedCode;
    }
}
