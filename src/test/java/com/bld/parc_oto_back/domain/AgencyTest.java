package com.bld.parc_oto_back.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AgencyTest {

    @Test
    void testAgencyCreation() {
        Address address = new Address("123 Main St", "12345", "City", "Country");
        Agency agency = new Agency(1L, address, "Test Agency");

        assertEquals(1L, agency.getId());
        assertEquals(address, agency.getAddress());
        assertEquals("Test Agency", agency.getName());
    }

    @Test
    void testNoArgsConstructor() {
        Agency agency = new Agency();

        assertNull(agency.getId());
        assertNull(agency.getAddress());
        assertNull(agency.getName());
    }

    @Test
    void testSettersAndGetters() {
        Agency agency = new Agency();
        Address address = new Address("456 Elm St", "67890", "Town", "State");

        agency.setId(2L);
        agency.setAddress(address);
        agency.setName("New Agency");

        assertEquals(2L, agency.getId());
        assertEquals(address, agency.getAddress());
        assertEquals("New Agency", agency.getName());
    }
}
