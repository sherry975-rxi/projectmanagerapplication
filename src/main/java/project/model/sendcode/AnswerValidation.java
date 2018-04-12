package project.model.sendcode;

public class AnswerValidation implements ValidationMethod {

    @Override
    public String performValidationMethod(String receipientPhoneNum, String email, String question, String msg) {

        return questionDecoder(question);
    }

    /**
     * returns the question associated with the question number saved in the user
     *
     * @param num saved question number
     * @return question associated with the number provided
     */
    public String questionDecoder(String num) {
        switch (num) {
            case "1":
                return "What is the name of your first pet?";
            case "2":
                return "What elementary school did you attend?";
            case "3":
                return "Where did you go for your honeymoon?";
            default:
                return "";
        }
    }

    @Override
    public boolean checkRightAnswer(String userInput, String rightAnswer) {
        return userInput.toUpperCase().equals(rightAnswer.toUpperCase());
    }
}
