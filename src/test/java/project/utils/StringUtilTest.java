package project.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Created by nuno on 01/05/17.
 */
public class StringUtilTest {
	@Test
	public void ensureLineBreakIsCorrect() throws Exception {
		assertEquals(String.format("%n"), new StringUtil().getLineBreak());
	}
}