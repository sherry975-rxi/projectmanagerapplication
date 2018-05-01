package project.model;

import org.springframework.hateoas.ResourceSupport;

public class JsonAsString  extends ResourceSupport {

    String textToReturn;

    /**
     * This class allows to return a String together with a Link
     * @param textToReturn The String that will be added to this object
     *
     */
    public JsonAsString(String textToReturn){

        this.textToReturn =  textToReturn;

    }

    /**
     * Returns the String parameter
     *
     * @return
     */
    public String getTextToReturn(){

        return this.textToReturn;
    }




}
