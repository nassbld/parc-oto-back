package com.bld.parc_oto_back.infrastructure;

import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.domain.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AgencyRepositoryTest {

//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private AgencyRepository agencyRepository;
//
//    @Test
//    void findByName_shouldReturnAgency_whenAgencyExists() {
//        Address address = new Address("123 Test St", "12345", "Test City", "Test Country");
//        Agency agency = new Agency(null, address, "Test Agency");
//        entityManager.persist(agency);
//        entityManager.flush();
//
//        Agency found = agencyRepository.findByName("Test Agency");
//
//        assertThat(found).isNotNull();
//        assertThat(found.getName()).isEqualTo("Test Agency");
//        assertThat(found.getAddress()).isEqualTo(address);
//    }
//
//    @Test
//    void findByName_shouldReturnNull_whenAgencyDoesNotExist() {
//        Agency found = agencyRepository.findByName("Non-existent Agency");
//
//        assertThat(found).isNull();
//    }
}
