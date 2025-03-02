package com.bld.parc_oto_back.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        User user = new User(1L, "M123", "Doe", "John", "john.doe@example.com", "1234567890", "123");

        assertEquals(1L, user.getId());
        assertEquals("M123", user.getMatricule());
        assertEquals("Doe", user.getLast_name());
        assertEquals("John", user.getFirst_name());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhone());
    }

    @Test
    void testNoArgsConstructor() {
        User user = new User();

        assertNull(user.getId());
        assertNull(user.getMatricule());
        assertNull(user.getLast_name());
        assertNull(user.getFirst_name());
        assertNull(user.getEmail());
        assertNull(user.getPhone());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();

        user.setId(2L);
        user.setMatricule("M456");
        user.setLast_name("Smith");
        user.setFirst_name("Jane");
        user.setEmail("jane.smith@example.com");
        user.setPhone("0987654321");

        assertEquals(2L, user.getId());
        assertEquals("M456", user.getMatricule());
        assertEquals("Smith", user.getLast_name());
        assertEquals("Jane", user.getFirst_name());
        assertEquals("jane.smith@example.com", user.getEmail());
        assertEquals("0987654321", user.getPhone());
    }
}
