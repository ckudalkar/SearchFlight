package com.myprojects.springboot.repository;

import com.myprojects.springboot.entity.MyFlight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FlightRepositoryTests {
    @Autowired
    private FlightRepository fr;

    @DisplayName("JUnit Test to get Flight List between given origin & Destination")
    @Test
    void givenOriginAndDestination_whenFindInfo_thenFlightList() {
        List<MyFlight> flightList = fr.findByOriginAndDestination("BOM", "DEL");
        assertThat(flightList).isNotEmpty()
                .allMatch(f -> "BOM".equals(f.getOrigin()) && "DEL".equals(f.getDestination()));
        assertThat(flightList.size()).isEqualTo(4);
    }
}
