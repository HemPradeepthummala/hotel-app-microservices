package org.example.search.service;


import org.example.search.exception.CityNotFoundException;
import org.example.search.model.Hotel;
import org.example.search.repository.HotelRepository;
import org.example.search.view.HotelView;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SearchServices {
    private final HotelRepository hotels;

    public SearchServices(HotelRepository hotelRepository) {
        this.hotels = hotelRepository;
    }

    public List<HotelView> searchHotel(String city) throws CityNotFoundException {
        if (city.isEmpty()) {
            throw new CityNotFoundException("empty city name");
        }
        List<Hotel> hotelByCity = hotels.findHotelByCity(city);
        return hotelByCity.stream().map(Hotel::project).toList();
    }
}
