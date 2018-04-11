package project.model.sendcode;




import java.util.HashMap;
import java.lang.Class;
import java.util.Map;


public class SendCodeFactory {

    private SMSSender smsSender;
    private EmailSender emailSender;

    private static final Map<String, MessageSender> codeSenderType = new HashMap<>();

    /**
     *
     */
    public void initMessageSenderType(){

        smsSender = new SMSSender();
        emailSender = new EmailSender();

        codeSenderType.put("1", smsSender);
        codeSenderType.put("2", emailSender);
    }

    /**
     *
     * @param codeSender
     * @return
     */
    public MessageSender getCodeSenderType (String codeSender){

        this.initMessageSenderType();

        return codeSenderType.get(codeSender);
    }



}
