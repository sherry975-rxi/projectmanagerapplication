package project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Group 3
 *
 */
public class UserTest {

	/**
	 * test getter/ setter Name
	 */
	@Test public
	void testGetName() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		assertEquals("Daniel", u1.getName());
	}

	@Test public
	void testSetName() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		u1.setName("Marta");

		assertEquals("Marta", u1.getName());
	}

	/**
	 * test getter/ setter Email
	 */
	@Test public
	void testGetEmail() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		assertEquals("daniel@gmail.com", u1.getEmail());
	}

	@Test public
	void testSetEmail() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		u1.setEmail("marta@gmail.com");

		assertEquals("marta@gmail.com", u1.getEmail());
	}

	/**
	 * test getter IDNumber
	 */
	@Test public
	void testGetIdNumber() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		assertEquals("01", u1.getIdNumber());
	}

	/**
	 * test getter/ setter Function
	 */
	@Test public
	void testGetFunction() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		assertEquals("Porteiro", u1.getFunction());
	}

	@Test public
	void testSetFunction() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		u1.setFunction("chefe");

		assertEquals("chefe", u1.getFunction());
	}

	/**
	 * test getter/ setter Phone
	 */
	@Test public
	void testGetPhone() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		assertEquals("910000000", u1.getPhone());
	}

	@Test public
	void testSetPhone() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		u1.setPhone("930000000");

		assertEquals("930000000", u1.getPhone());
	}

	/**
	 * tests the getAddress method.
	 */
	@Test public
	void testGetAddress() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");
		Address a1 = new Address("Rua A", "3700-243", "Aveiro", "Aveiro", "Portugal");
		Address a2 = new Address("Rua A", "3700-243", "Aveiro", "Aveiro", "Portugal");
		u1.addAddress(a1);
		u1.addAddress(a2);

		List<Address> expResult = new ArrayList<Address>();
		expResult.add(a1);
		expResult.add(a2);

		assertEquals(expResult, u1.getAddressList());

	}

	/**
	 * tests the getAddress method with an empty address list.
	 */
	@Test public
	void testGetAddress_emptyList() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		List<Address> expResult = new ArrayList<Address>();

		assertEquals(expResult, u1.getAddressList());

	}

	/**
	 * tests the addAddress method.
	 */
	@Test public
	void testAddAddress() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");
		Address a1 = new Address("Rua A", "3700-243", "Aveiro", "Aveiro", "Portugal");
		Address a2 = new Address("Rua A", "3700-243", "Aveiro", "Aveiro", "Portugal");
		u1.addAddress(a1);
		u1.addAddress(a2);

		List<Address> expResult = new ArrayList<Address>();
		expResult.add(a1);
		expResult.add(a2);

		assertEquals(expResult, u1.getAddressList());
	}

	/**
	 * tests the isSystemUserStateActive method. Since the every user is initialized
	 * as "Active" (true), the assert True of the isSystemUserState method must be
	 * true.
	 */
	@Test public
	void isSystemUserStateActive() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		assertTrue(u1.isSystemUserStateActive());
	}

	@Test public
	void getProfile() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		Profile visitor = Profile.VISITOR;

		assertEquals(visitor, u1.getUserProfile());
	}

	/**
	 * tests the changeSystemUserState to false.
	 */
	@Test public
	void testChangeSystemUserState() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		u1.setSystemUserState(false);

		assertEquals(u1.isSystemUserStateActive(), false);
	}

	/**
	 * tests the changeSystemUserState.
	 */
	@Test public
	void testChangeSystemUserState_FalseAndTrue() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		u1.setSystemUserState(true);

		assertEquals(u1.isSystemUserStateActive(), true);

	}

	/**
	 * Tests the createAddress method, by comparing if all fields of the address
	 * added (to the user address list) are equal to Strings previously defined.
	 */
	@Test public
	void testCreateAddress() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		u1.addAddress(u1.createAddress("street", "zipCode", "city", "district", "country"));

		String street = "street";
		String zipCode = "zipCode";
		String city = "city";
		String district = "district";
		String country = "country";

		assertEquals(street, u1.getAddressList().get(0).getStreet());
		assertEquals(zipCode, u1.getAddressList().get(0).getZipCode());
		assertEquals(city, u1.getAddressList().get(0).getCity());
		assertEquals(district, u1.getAddressList().get(0).getDistrict());
		assertEquals(country, u1.getAddressList().get(0).getCountry());
	}

	/**
	 * tests the equals methods on two Users with the same email.
	 */
	@Test public
	void testUserEquals() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");
		User u2 = new User("Joao", "daniel@gmail.com", "02", "Serralheiro", "960000000");

		assertTrue(u1.equals(u2));

	}

	/**
	 * tests the equals methods on two Users with different email.
	 */
	@Test public
	void testUserEquals_False() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");
		User u2 = new User("Joao", "joao@gmail.com", "02", "Serralheiro", "960000000");

		assertFalse(u1.equals(u2));

	}

	/**
	 * Tests the setUserProfile method by setting the user profile to collaborator.
	 */
	@Test public
	void setUserProfileCollaborator() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		u1.setUserProfile(Profile.COLLABORATOR);

		assertEquals(Profile.COLLABORATOR, u1.getUserProfile());

	}

	/**
	 * Tests the setUserProfile method by setting the user profile to Director.
	 */
	@Test public
	void setUserProfileDirector() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		u1.setUserProfile(Profile.DIRECTOR);

		assertEquals(Profile.DIRECTOR, u1.getUserProfile());

	}

	/**
	 * Tests the setUserProfile method by setting the user profile to Visitor.
	 */
	@Test public
	void setUserProfileVisitor() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Porteiro", "910000000");

		u1.setUserProfile(Profile.VISITOR);

		assertEquals(Profile.VISITOR, u1.getUserProfile());

	}
}
