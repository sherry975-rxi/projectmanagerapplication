package project.model.sendcode;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SendCodeFactoryTest {

	private SMSSender smsSender;
	private EmailSender emailSender;
	private SendCodeFactory sendCodeFactory;
	private static final Map<String, ValidationMethod> codeSenderType = new HashMap<>();
	private AnswerValidation answerValidation;

	@Before
	public void setup() {

		smsSender = new SMSSender();
		emailSender = new EmailSender();
		answerValidation = new AnswerValidation();
		sendCodeFactory = new SendCodeFactory();

	}

	@Test
	public void getCodeSenderTypeTest() {

		assertEquals(sendCodeFactory.getCodeSenderType("1").getClass(), Optional.of(smsSender).getClass());

		assertEquals(sendCodeFactory.getCodeSenderType("2").getClass(), Optional.of(emailSender).getClass());

		assertEquals(sendCodeFactory.getCodeSenderType("3").getClass(), Optional.of(answerValidation).getClass());

		assertFalse(sendCodeFactory.getCodeSenderType("0").isPresent());

	}
}
