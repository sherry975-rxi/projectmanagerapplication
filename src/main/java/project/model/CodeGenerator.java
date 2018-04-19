package project.model;

import org.springframework.hateoas.ResourceSupport;

import java.util.Random;

public class CodeGenerator extends ResourceSupport {


    public String generateCode() {

        Random rand = new Random();
        Integer randomNumber = 100000 + rand.nextInt(900000);
        return randomNumber.toString();
    }

}
