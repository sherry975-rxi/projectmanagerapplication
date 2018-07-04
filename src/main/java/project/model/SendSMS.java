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
            accountSid = "ACce621bdb7d6153096a909247191dcbd4";
            authToken = "4debf2603070c7ddf6cb37d55787d263";

        }



        Twilio.init(accountSid, authToken);


        /*
        This is the host number provided by Twilio's Service
         */
        if(numberProvidedByTwilio == null){
            numberProvidedByTwilio = "ProjectApp";
        }

        /*
            Test number. Must be a registered number on Twilio's account.
            In this case, the registered number on the Twilio account is +351937429087 number (João Leite's number)
         */



        String appendToString = "+351" + numberToSend;


        return Message.creator(new PhoneNumber(appendToString),
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
