package project.model.sendcode;




import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class SendCodeFactory {


    private static final Map<String, ValidationMethod> codeSenderType = new HashMap<>();

    /**
     * This method creates business objects depending on the argument
     */
    public void initMessageSenderType(){

        ValidationMethod smsSender = new SMSSender();
        ValidationMethod emailSender = new EmailSender();
        ValidationMethod answerValidation = new AnswerValidation();

        codeSenderType.put("1", smsSender);
        codeSenderType.put("2", emailSender);
        codeSenderType.put("3", answerValidation);
    }

    /**
     *
     * @param codeSender
     * @return Valiad
     */
    public Optional<ValidationMethod> getCodeSenderType(String codeSender) {

        this.initMessageSenderType();
        int num = -1;
        try {
            num = Integer.parseInt(codeSender);
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
        if (num > 0 && num <= codeSenderType.size()) {
            return Optional.of(codeSenderType.get(codeSender));
        } else {
            return Optional.empty();
        }
    }



}
