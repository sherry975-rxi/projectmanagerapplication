package project.model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.hateoas.ResourceSupport;

public class SendSMS extends ResourceSupport {

   private String accountSid;
   private String authToken;
   private String numberProvidedByTwilio;

    public void setAccountSid(String accountSid){
        this.accountSid = accountSid;
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }


    public void setNumberProvidedByTwilio(String numberByTwilio){
        this.numberProvidedByTwilio = numberByTwilio;

    }


    /*
        Set overrideNumber to TRUE or overrideAuth to TRUE only for JUnit tests
     */

    public Message.Status sendMessage(String messageToSend, String numberToSend, Boolean overrideAccountAuth){

        /*
         Settings taken from twilio.com/user/account

             RegisteredEmail:   isepswitch3@gmail.com
             Password:          Switch_Isep2018

             Check Account_SID and Auth_Token in website's account

         */
        if(!overrideAccountAuth){
            accountSid = "AC4ffe069234a3ce253b84b7f7ed678407";
            authToken = "7b30788fd931e158fe3846ec7e3866a0";

        }



        Twilio.init(accountSid, authToken);


        /*
        This is the host number provided by Twilio's Service
         */
        if(numberProvidedByTwilio == null){
            numberProvidedByTwilio = "+17044577646";
        }

        /*
            Test number. Must be a registered number on Twilio's account.
            In this case, the registered number on the Twilio account is +351937429087 number (João Leite's number)
         */


        String overrideNumber = "+351911790134";


        return Message.creator(new PhoneNumber(overrideNumber),
                new PhoneNumber(numberProvidedByTwilio),
                messageToSend).create().getStatus();


    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
