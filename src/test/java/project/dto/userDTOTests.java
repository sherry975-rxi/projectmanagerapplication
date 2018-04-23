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

		user = new UserDTO("name", "email", "idNumber", "function", "phone", "password", "1", "test");
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
		//create user3
		user3 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");
		Address a1 = new Address("Rua A", "3700-243", "Aveiro", "Aveiro", "Portugal");
		//add address in user3
		user3.addAddress(a1);
		//create userDTO (user 2) from user3
		user2 = new UserDTO(user3);
		//checks if the size of address list are the same
		assertEquals(1, user2.getAddressList().size());
		//checks if the existing address is the same in userDTO and user
		assertEquals(a1, user2.getAddressList().get(0));
		//checks if the profile is the same in userDTO and user
		assertEquals(user3.getUserProfile(), (user2.getUserProfile()));

	}
}
