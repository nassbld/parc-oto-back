package com.bld.parc_oto_back.domain;

import com.bld.parc_oto_back.domain.enums.VehicleStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testVehicleCreation() {
        Agency testAgency = new Agency();
        testAgency.setId(1L);
        testAgency.setName("Test Agency");

        VehicleType testType = new VehicleType();
        testType.setId(1L);
        testType.setBrand("Renault");
        testType.setModel("Clio");
        testType.setImageUrl("url_to_image");

        LocalDateTime now = LocalDateTime.now();
        Vehicle vehicle = new Vehicle(1L, "ABC123", testType, VehicleStatus.AVAILABLE, testAgency, now.plusYears(1), now.plusMonths(6));

        assertEquals(1L, vehicle.getId());
        assertEquals("ABC123", vehicle.getLicensePlate());
        assertEquals(testType, vehicle.getType());
        assertEquals("Renault", vehicle.getType().getBrand());
        assertEquals("Clio", vehicle.getType().getModel());
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
        assertEquals(testAgency, vehicle.getAgency());
        assertEquals(now.plusYears(1), vehicle.getEndInsurance());
        assertEquals(now.plusMonths(6), vehicle.getEndTechnicalControl());
    }

    @Test
    void testNoArgsConstructor() {
        Vehicle vehicle = new Vehicle();

        assertNull(vehicle.getId());
        assertNull(vehicle.getLicensePlate());
        assertNull(vehicle.getType());
        assertNull(vehicle.getStatus());
        assertNull(vehicle.getAgency());
        assertNull(vehicle.getEndInsurance());
        assertNull(vehicle.getEndTechnicalControl());
    }

    @Test
    void testSettersAndGetters() {
        Vehicle vehicle = new Vehicle();
        Agency agency = new Agency();
        VehicleType vehicleType = new VehicleType();
        vehicleType.setBrand("Toyota");
        vehicleType.setModel("Corolla");
        vehicleType.setImageUrl("url_to_toyota_image");
        LocalDateTime now = LocalDateTime.now();

        vehicle.setId(2L);
        vehicle.setLicensePlate("XYZ789");
        vehicle.setType(vehicleType);
        vehicle.setStatus(VehicleStatus.UNDER_MAINTENANCE);
        vehicle.setAgency(agency);
        vehicle.setEndInsurance(now.plusYears(1));
        vehicle.setEndTechnicalControl(now.plusMonths(6));

        assertEquals(2L, vehicle.getId());
        assertEquals("XYZ789", vehicle.getLicensePlate());
        assertEquals(vehicleType, vehicle.getType());
        assertEquals("Toyota", vehicle.getType().getBrand());
        assertEquals("Corolla", vehicle.getType().getModel());
        assertEquals(VehicleStatus.UNDER_MAINTENANCE, vehicle.getStatus());
        assertEquals(agency, vehicle.getAgency());
        assertEquals(now.plusYears(1), vehicle.getEndInsurance());
        assertEquals(now.plusMonths(6), vehicle.getEndTechnicalControl());
    }
}
