package project.model.sendcode;




import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class SendCodeFactory {

    private ValidationMethod smsSender;
    private ValidationMethod emailSender;
    private static final Map<String, ValidationMethod> codeSenderType = new HashMap<>();
    private ValidationMethod answerValidation;

    /**
     *
     */
    public void initMessageSenderType(){

        smsSender = new SMSSender();
        emailSender = new EmailSender();
        answerValidation = new AnswerValidation();

        codeSenderType.put("1", smsSender);
        codeSenderType.put("2", emailSender);
        codeSenderType.put("3", answerValidation);
    }

    /**
     *
     * @param codeSender
     * @return
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
