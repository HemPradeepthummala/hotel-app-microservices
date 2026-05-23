package org.example.search.repository;

import org.example.search.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {
  List<Hotel> findHotelByCity(String city);
}
