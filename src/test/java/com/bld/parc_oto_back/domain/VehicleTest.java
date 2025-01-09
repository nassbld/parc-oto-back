package com.bld.parc_oto_back.domain;

import com.bld.parc_oto_back.domain.enums.VehicleStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testVehicleCreation() {
        Agency testAgency = new Agency();
        testAgency.setId(1L);
        testAgency.setName("Test Agency");

        Vehicle vehicle = new Vehicle(1L, "ABC123", "Model X", "Tesla", VehicleStatus.AVAILABLE, testAgency);

        assertEquals(1L, vehicle.getId());
        assertEquals("ABC123", vehicle.getLicense_plate());
        assertEquals("Model X", vehicle.getModel());
        assertEquals("Tesla", vehicle.getBrand());
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
    }

    @Test
    void testNoArgsConstructor() {
        Vehicle vehicle = new Vehicle();

        assertNull(vehicle.getId());
        assertNull(vehicle.getLicense_plate());
        assertNull(vehicle.getModel());
        assertNull(vehicle.getBrand());
        assertNull(vehicle.getStatus());
    }

    @Test
    void testSettersAndGetters() {
        Vehicle vehicle = new Vehicle();

        vehicle.setId(2L);
        vehicle.setLicense_plate("XYZ789");
        vehicle.setBrand("Honda");
        vehicle.setModel("Civic");
        vehicle.setStatus(VehicleStatus.UNDER_MAINTENANCE);

        assertEquals(2L, vehicle.getId());
        assertEquals("XYZ789", vehicle.getLicense_plate());
        assertEquals("Civic", vehicle.getModel());
        assertEquals("Honda", vehicle.getBrand());
        assertEquals(VehicleStatus.UNDER_MAINTENANCE, vehicle.getStatus());
    }
}
