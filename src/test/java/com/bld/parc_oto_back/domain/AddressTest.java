package com.bld.parc_oto_back.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void testAddressCreation() {
        Address address = new Address("123 Main St", "12345", "City", "Country");

        assertEquals("123 Main St", address.getStreet());
        assertEquals("12345", address.getPostalCode());
        assertEquals("City", address.getCity());
        assertEquals("Country", address.getCountry());
    }

    @Test
    void testAddressToString() {
        Address address = new Address("123 Main St", "12345", "City", "Country");
        String expectedToString = "Address(street=123 Main St, postalCode=12345, city=City, country=Country)";

        assertEquals(expectedToString, address.toString());
    }

    @Test
    void testNoArgsConstructor() {
        Address address = new Address();

        assertNull(address.getStreet());
        assertNull(address.getPostalCode());
        assertNull(address.getCity());
        assertNull(address.getCountry());
    }
}
