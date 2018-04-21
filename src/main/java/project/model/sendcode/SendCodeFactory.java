package project.model.sendcode;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class SendCodeFactory {


    private static final Map<String, ValidationMethod> codeSenderType = new HashMap<>();

    /**
     * This method creates business objects depending on the argument
     */
    private void initMessageSenderType() {

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

        Optional<ValidationMethod> opt = Optional.empty();

        this.initMessageSenderType();
        int num;
        try {
            num = Integer.parseInt(codeSender);

            if (num > 0 && num <= codeSenderType.size()) {
                opt = Optional.of(codeSenderType.get(codeSender));
            }

        } catch (NumberFormatException nfe) {
            Logger log = LoggerFactory.getLogger(SendCodeFactory.class);
            log.error("Not a valid number.", nfe);
        }
        return opt;
    }



}
