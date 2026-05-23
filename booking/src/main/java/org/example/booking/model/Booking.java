package org.example.booking.model;

import org.example.booking.view.BookingDetailsView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Booking {
	@Id
	private final String bookingId;
	private final String userId;
	private final String hotelId;
	private final Integer rooms;

	public Booking(String bookingId, String userId, String hotelId, Integer rooms) {
		this.bookingId = bookingId;
		this.userId = userId;
		this.hotelId = hotelId;
		this.rooms = rooms;
	}

	public String generateReceipt() {
		return String.format("you have booked %s rooms", rooms);
	}

	public BookingDetailsView project() {
		return new BookingDetailsView(bookingId, userId, hotelId, rooms);
	}
}
