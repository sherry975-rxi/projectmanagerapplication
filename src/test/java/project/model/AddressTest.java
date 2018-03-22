package project.model;

import org.junit.Test;
import project.Services.ProjectService;

import static org.junit.Assert.*;

public class AddressTest {

	/**
	 * This test tests the get of the field street
	 */
	@Test
	public void testGetChangeOnOneFieldOfAddress_1() {
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		assertEquals("Rua Direita", casa.getStreet());
	}

	/**
	 * This test tests the get of the field zipCode
	 */
	@Test
	public void testGetChangeOnOneFieldOfAddress_2() {
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		assertEquals("4356-245", casa.getZipCode());
	}

	/**
	 * This test tests the get of the field city
	 */
	@Test
	public void testGetChangeOnOneFieldOfAddress_3() {
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		assertEquals("Gondomar", casa.getCity());
	}

	/**
	 * This test tests the get of the field district
	 */
	@Test
	public void testGetChangeOnOneFieldOfAddress_4() {
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		assertEquals("Porto", casa.getDistrict());
	}

	/**
	 * This test tests the get of the field country
	 */
	@Test
	public void testGetChangeOnOneFieldOfAddress_5() {
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		assertEquals("Portugal", casa.getCountry());
	}

	/**
	 * This test creates a new address with the provided fields then it changes all
	 * the fields. In this case changes to empty fields.
	 */
	@Test
	public void testSetters() {
		Address casa = new Address(null, null, null, null, null);

		casa.setStreet("Rua");
		assertEquals("Rua", casa.getStreet());

		casa.setZipCode("4510-500");
		assertEquals("4510-500", casa.getZipCode());

		casa.setCity("Porto");
		assertEquals("Porto", casa.getCity());

		casa.setDistrict("Porto");
		assertEquals("Porto", casa.getDistrict());

		casa.setCountry("Portugal");
		assertEquals("Portugal", casa.getCountry());

	}

	/**
	 * Tests the HashCodes of Address class. They are the same if the streets are
	 * the same
	 */
	@Test
	public void testHashCode() {

		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");
		Address casa2 = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");
		Address casa3 = new Address("Rua Esquerda", "4356-245", "Gondomar", "Porto", "Portugal");
		Address casa4 = new Address(null, "4356-245", "Gondomar", "Porto", "Portugal");

		int result = (31*3) + casa.getStreet().hashCode();

		assertTrue(casa.hashCode() == result);
		assertTrue(casa.hashCode() == casa2.hashCode());
		assertFalse(casa.hashCode() == casa4.hashCode());
	    assertFalse(casa.hashCode() == casa3.hashCode());

	}

	/**
	 * Tests equals method in the Adress Class
	 */
	@Test
	public void testEquals() {

		ProjectService projectRep = new ProjectService();
		// This two addresses are the same
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");
		Address casa2 = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		// This address is null
		Address casa3 = null;

		// This two addresses have null streets
		Address casa4 = new Address(null, "4356-245", "Gondomar", "Porto", "Portugal");
		Address casa5 = new Address(null, "4356-245", "Gondomar", "Porto", "Portugal");

		// This address has a different street
		Address casa6 = new Address("Rua Esquerda", "4356-245", "Gondomar", "Porto", "Portugal");

		// The two objects have the same attributes
		assertTrue(casa.equals(casa2));

		// Casa3 is a null object
		assertFalse(casa.equals(casa3));

		// projectRep is an instance of another class
		assertFalse(casa.equals(projectRep));

		// Both addresses have null streets
		assertTrue(casa4.equals(casa5));

		// One address has a null address, and other does not
		assertFalse(casa4.equals(casa));

		// Addresses have differente streets
		assertFalse(casa6.equals(casa));

	}

}
