package org.example.search.controller;


import org.example.search.exception.CityNotFoundException;
import org.example.search.service.SearchServices;
import org.example.search.view.HotelView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HotelController {
    private final SearchServices searchServices;

    public HotelController(SearchServices searchServices) {
        this.searchServices = searchServices;
    }

    @GetMapping("/api/search/hotels")
    public ResponseEntity<List<HotelView>> search(@RequestParam String city){
	    try {
		    return ResponseEntity.ok(searchServices.searchHotel(city));
	    } catch (CityNotFoundException e) {
		    return ResponseEntity.badRequest().build();
	    }
    }
}
