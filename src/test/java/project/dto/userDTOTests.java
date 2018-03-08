package project.dto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Address;
import project.model.User;

import static org.junit.Assert.assertEquals;

public class userDTOTests {

	UserDTO user;
	UserDTO user2;
	User user3;

	@Before
	public void setUp() {

		user = new UserDTO("name", "email", "idNumber", "function", "phone", "password");
		user.setUserAddress( "street", "zipCode", "city",
				"district", "country");
	}

	@After
	public void tearDown() {
		user = null;
		user2 = null;
		user3 = null;
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
	/**
	 * Tests the constructor of the userDTO from an existing user
	 */
	@Test
	public void testConstructorUserDTO(){
		user3 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");
		Address a1 = new Address("Rua A", "3700-243", "Aveiro", "Aveiro", "Portugal");
		user3.addAddress(a1);

		user2 = new UserDTO(user3);
		assertEquals(1, user2.getAddressList().size());

		assertEquals(a1, user2.getAddressList().get(0));

	}
}
