package project.model;

import org.springframework.hateoas.ResourceSupport;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JsonAsString)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        JsonAsString that = (JsonAsString) o;
        return Objects.equals(textToReturn, that.textToReturn);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), textToReturn);
    }
}
