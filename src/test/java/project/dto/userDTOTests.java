package project.dto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class userDTOTests {

	UserDTO user;

	@Before
	public void setUp() {

		user = new UserDTO("name", "email", "idNumber", "function", "phone", "password");
		user.setUserAddress( "street", "zipCode", "city",
				"district", "country");
	}

	@After
	public void tearDown() {
		user = null;
	}

	/**
	 * Tests the getters of the userDTO class
	 */
	@Test
	public void testGetters() {

		assertEquals("name", user.getName());
		assertEquals("email", user.getEmail());
		assertEquals("idNumber", user.getIdNumber());
		assertEquals("function", user.getFunction());
		assertEquals("phone", user.getPhone());
		assertEquals("password", user.getPassword());
		assertEquals("street", user.getStreet());
		assertEquals("zipCode", user.getZipCode());
		assertEquals("city", user.getCity());
		assertEquals("district", user.getDistrict());
		assertEquals("country", user.getCountry());
	}
}
