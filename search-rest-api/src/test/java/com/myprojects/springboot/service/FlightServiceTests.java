package com.myprojects.springboot.service;

import com.myprojects.springboot.dto.FlightDto;
import com.myprojects.springboot.entity.MyFlight;
import com.myprojects.springboot.exception.FlightInfoNotFoundException;
import com.myprojects.springboot.repository.FlightRepository;
import com.myprojects.springboot.service.impl.MyFlightServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTests {
    @Mock
    private FlightRepository fr;
    @InjectMocks
    private MyFlightServiceImpl fs;

    private List<MyFlight> myFlightList;

    @BeforeEach
    public void setup(){
        //create the data before every test execution
        myFlightList = this.createTestListFlightData();
    }

    @DisplayName("JUnit test to get flight details by origin and destination")
    @Test
    public void testGetFlightListByOriginAndDest(){
        // Arrange the data
        myFlightList = myFlightList.stream()
                .filter(f -> "BOM".equals(f.getOrigin()) && "DEL".equals(f.getDestination()))
                .collect(Collectors.toList());

        // Add the behaviour
        when(fr.findByOriginAndDestination("BOM", "DEL")).thenReturn(myFlightList);
        List<FlightDto> flightDtoList = this.fs.getFlights("BOM", "DEL", Optional.empty(), Optional.empty());

        // Testing the Result
        assertThat(flightDtoList).isNotEmpty()
                .allMatch(f -> "BOM".equals(f.getOrigin()) && "DEL".equals(f.getDestination()));

        //Verify the behaviour
        verify(this.fr).findByOriginAndDestination("BOM", "DEL");
    }

    @DisplayName("JUnit test get flight details sorted by price in ascending order")
    @Test
    void testGetFlightListSortByPriceAscending() {

        //Arrange the Data
        myFlightList = myFlightList.stream()
                .filter(f -> "BOM".equals(f.getOrigin()) && "DEL".equals(f.getDestination()))
                .sorted(Comparator.comparing(MyFlight::getPrice))
                .collect(Collectors.toList());

        //Add the behaviour
        when(fr.findByOriginAndDestinationOrderByPriceAsc ("BOM", "DEL")).thenReturn(myFlightList);
        Optional <String> price = Optional.ofNullable("asc");
        List<FlightDto> flightDtoList = this.fs.getFlights("BOM","DEL", price,
                Optional.empty());

        // Test The result
        assertThat(flightDtoList).isSortedAccordingTo(Comparator.comparing(FlightDto::getPrice));

        //Verify the behaviour
        verify(this.fr).findByOriginAndDestinationOrderByPriceAsc("BOM", "DEL");
    }

    @DisplayName("JUnit test to get flight details sorted by price in descending order")
    @Test
    void testGetFlightListSortByPriceDescending() {

        //Arrange the Data
        myFlightList = myFlightList.stream()
                .filter(f -> "BOM".equals(f.getOrigin()) && "DEL".equals(f.getDestination()))
                .sorted(Comparator.comparing(MyFlight::getPrice).reversed())
                .collect(Collectors.toList());

        //Add the behaviour
        when(fr.findByOriginAndDestinationOrderByPriceDesc("BOM", "DEL")).thenReturn(myFlightList);
        Optional <String> price = Optional.ofNullable("desc");
        List<FlightDto> flightDtoList = this.fs.getFlights("BOM","DEL", price,
                Optional.empty());

        // Test The result
        assertThat(flightDtoList).isSortedAccordingTo(Comparator.comparing(FlightDto::getPrice).reversed());

        //Verify the behaviour
        verify(this.fr).findByOriginAndDestinationOrderByPriceDesc("BOM", "DEL");
    }

    @DisplayName("JUnit test to get flight details sorted by Duration in ascending")
    @Test
    void testGetFlightListSortByDurationAscending() {

        //Arrange the Data
        myFlightList = myFlightList.stream()
                .filter(f -> "BOM".equals(f.getOrigin()) && "DEL".equals(f.getDestination()))
                .collect(Collectors.toList());

        //Add the behaviour
        when(fr.findByOriginAndDestination("BOM", "DEL")).thenReturn(myFlightList);
        Optional <String> duration = Optional.ofNullable("asc");
        List<FlightDto> flightList = this.fs.getFlights("BOM", "DEL", Optional.empty(),
                duration);

        //Test The Result
        assertThat(flightList).isSortedAccordingTo(Comparator.comparing(FlightDto::getDuration));

        //Verify the behaviour
        verify(this.fr).findByOriginAndDestination("BOM", "DEL");
    }


    @DisplayName("JUnit test to get flight details sorted by Duration in descending")
    @Test
    void testGetFlightListSortByDurationDescending() {

        //Arrange the Data
        myFlightList = myFlightList.stream()
                .filter(f -> "BOM".equals(f.getOrigin()) && "DEL".equals(f.getDestination()))
                .collect(Collectors.toList());

        //Add the behaviour
        when(fr.findByOriginAndDestination("BOM", "DEL")).thenReturn(myFlightList);
        Optional <String> duration = Optional.ofNullable("desc");
        List<FlightDto> flightList = this.fs.getFlights("BOM", "DEL", Optional.empty(),
                duration);

        //Test The Result
        assertThat(flightList).isSortedAccordingTo(Comparator.comparing(FlightDto::getDuration).reversed());

        //Verify the behaviour
        verify(this.fr).findByOriginAndDestination("BOM", "DEL");
    }

    @DisplayName("JUnit test for Record not found exception for given inputs" )
    @Test
    void testGetFlightListWithNotExistingOriginAndDestination() {

        //Arrange the Data
        myFlightList = myFlightList.stream()
                .filter(f -> "GOA".equals(f.getOrigin()) && "PUNE".equals(f.getDestination()))
                .collect(Collectors.toList());

        // Add Behaviour
        when(fr.findByOriginAndDestination("GOA", "PUNE")).thenReturn(myFlightList);

        // Test The result
        assertThrows(FlightInfoNotFoundException.class,()->
                this.fs.getFlights("GOA", "PUNE", Optional.empty(), Optional.empty()));

        //Verify The Behaviour
        verify(this.fr).findByOriginAndDestination("GOA", "PUNE");
    }


    private List<MyFlight> createTestListFlightData() {
        List<MyFlight> flightList = new ArrayList<MyFlight>();
        MyFlight flight = new MyFlight();
        flight.setId(1L);
        flight.setFlightNum ("F101");
        flight.setOrigin("BOM");
        flight.setDestination("DEL");
        flight.setArrivalTime(LocalDateTime.of(2023, 6, 12, 20, 30));
        flight.setDepartureTime(LocalDateTime.of(2023, 6, 12, 21, 30));
        flight.setPrice(80);
        flightList.add(flight);
        flight = new MyFlight();
        flight.setId(2L);
        flight.setFlightNum("G101");
        flight.setOrigin("BOM");
        flight.setDestination("DEL");
        flight.setArrivalTime(LocalDateTime.of(2023, 6, 12, 18, 00));
        flight.setDepartureTime(LocalDateTime.of(2023, 6, 12, 19, 30));
        flight.setPrice(100);
        flightList.add(flight);
        flight = new MyFlight();
        flight.setId(3L);
        flight.setFlightNum("F201");
        flight.setOrigin("BOM");
        flight.setDestination("DEL");
        flight.setArrivalTime(LocalDateTime.of(2023, 6, 12, 21, 15));
        flight.setDepartureTime(LocalDateTime.of(2023, 6, 12, 22, 30));
        flight.setPrice(80);
        flightList.add(flight);
        flight = new MyFlight();
        flight.setId(4L);
        flight.setFlightNum("G01");
        flight.setOrigin("BOM");
        flight.setDestination("DEL");
        flight.setArrivalTime(LocalDateTime.of(2023, 6, 12, 20, 20));
        flight.setDepartureTime(LocalDateTime.of(2023, 6, 12, 21, 30));
        flight.setPrice(80);
        flightList.add(flight);
        flight = new MyFlight();
        flight.setId(5L);
        flight.setFlightNum("E201");
        flight.setOrigin("DEL");
        flight.setDestination("BOM");
        flight.setArrivalTime(LocalDateTime.of(2023, 6, 12, 18, 45));
        flight.setDepartureTime(LocalDateTime.of(2023, 6, 12, 20, 15));
        flight.setPrice(80);
        flightList.add(flight);
        return flightList;
    }

}
