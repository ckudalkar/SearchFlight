package com.myprojects.springboot.repository;

import com.myprojects.springboot.entity.MyFlight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<MyFlight, Long> {

    List<MyFlight> findByOriginAndDestination(String origin, String destination);

    List<MyFlight> findByOriginAndDestinationOrderByPriceAsc(String origin, String destination);

    List<MyFlight> findByOriginAndDestinationOrderByPriceDesc(String origin, String destination);

    List<MyFlight> findByOrigin(String origin);

    List<MyFlight> findByOriginOrderByPriceAsc(String origin);

    List<MyFlight> findByOriginOrderByPriceDesc(String origin);
    List<MyFlight> findByDestination(String destination);

}
