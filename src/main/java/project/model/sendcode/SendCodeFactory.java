package project.model.sendcode;


import java.util.HashMap;
import java.util.Map;



public class SendCodeFactory {


    private SMSSender smsSender;
    private EmailSender emailSender;

    private static final Map <String, MessageSender> codeSenderType = new HashMap<>();

    /**
     *
     */
    public void initMessageSenderType(){

        codeSenderType.put("1", smsSender);
        codeSenderType.put("2", emailSender);
    }

    /**
     *
     * @param codeSender
     * @return
     */
    public MessageSender getCodeSenderType (String codeSender){

        return codeSenderType.get(codeSender);
    }



}
