package test.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.project.model.Address;

class AddressTest {

	/**
	 * This test tests the get of the field street
	 */
	@Test
	void testGetChangeOnOneFieldOfAddress_1() {
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		assertEquals("Rua Direita", casa.getStreet());
	}

	/**
	 * This test tests the get of the field zipCode
	 */
	@Test
	void testGetChangeOnOneFieldOfAddress_2() {
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		assertEquals("4356-245", casa.getZipCode());
	}

	/**
	 * This test tests the get of the field city
	 */
	@Test
	void testGetChangeOnOneFieldOfAddress_3() {
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		assertEquals("Gondomar", casa.getCity());
	}

	/**
	 * This test tests the get of the field district
	 */
	@Test
	void testGetChangeOnOneFieldOfAddress_4() {
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		assertEquals("Porto", casa.getDistrict());
	}

	/**
	 * This test tests the get of the field country
	 */
	@Test
	void testGetChangeOnOneFieldOfAddress_5() {
		Address casa = new Address("Rua Direita", "4356-245", "Gondomar", "Porto", "Portugal");

		assertEquals("Portugal", casa.getCountry());
	}

	/**
	 * This test creates a new address with the provided fields then it changes all
	 * the fields. In this case changes to empty fields.
	 */
	@Test
	void testSetters() {
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

}
