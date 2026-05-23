package org.example.search.model;

import org.example.search.view.HotelView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Hotel {
  @Id
  private final String hotelId;
  private final String name;
  private final int rooms;
  private final String city;

  public Hotel(String hotelId, String name, int rooms, String city) {
    this.hotelId = hotelId;
    this.name = name;
    this.rooms = rooms;
    this.city = city;
  }

  public HotelView project() {
    return new HotelView(this.hotelId, this.name, this.rooms, this.city);
  }
}
