package project.model.sendcode;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SendCodeFactoryTest  {

	private SMSSender smsSender;
	private EmailSender emailSender;
	private SendCodeFactory sendCodeFactory;
	private static final Map<String, ValidationMethod> codeSenderType = new HashMap<>();
	private AnswerValidation answerValidation;
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {

		smsSender = new SMSSender();
		emailSender = new EmailSender();
		answerValidation = new AnswerValidation();
		sendCodeFactory = new SendCodeFactory();

	}

	@Test
	public void getCodeSenderTypeTest() throws NumberFormatException  {

		assertEquals(sendCodeFactory.getCodeSenderType("1").get().getClass(), Optional.of(smsSender).get().getClass());

		assertEquals(sendCodeFactory.getCodeSenderType("2").get().getClass(), Optional.of(emailSender).get().getClass());

		assertEquals(sendCodeFactory.getCodeSenderType("3").get().getClass(), Optional.of(answerValidation).get().getClass());

		assertFalse(sendCodeFactory.getCodeSenderType("0").isPresent());


		sendCodeFactory.getCodeSenderType("a");




	}
}
