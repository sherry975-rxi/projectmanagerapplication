package project.model;

import org.springframework.hateoas.ResourceSupport;

import java.util.Random;

public class CodeGenerator extends ResourceSupport {


    public String generateCode() {

        Random rand = new Random();
        int randomNumber = rand.nextInt(1000000);
        return String.format("%06d", randomNumber);
    }

}
