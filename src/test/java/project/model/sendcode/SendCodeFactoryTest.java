package project.model.sendcode;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SendCodeFactoryTest {

	private SMSSender smsSender;
	private EmailSender emailSender;
	private SendCodeFactory sendCodeFactory;
	private MessageSender messageSender;
	private static final Map<String, MessageSender> codeSenderType = new HashMap<>();

	@Before
	public void setup() {

		smsSender = new SMSSender();
		emailSender = new EmailSender();

		codeSenderType.put("1", smsSender);
		codeSenderType.put("2", emailSender);
		sendCodeFactory = new SendCodeFactory();

	}

	@Test
	public void getCodeSenderTypeTest() {

		assertEquals(sendCodeFactory.getCodeSenderType("1").getClass(), smsSender.getClass());

	}
}
