package org.example.booking.controller;

import org.example.booking.model.Booking;
import org.example.booking.service.BookingService;
import org.example.booking.view.BookingDetailsView;
import org.example.booking.view.BookingRequest;
import org.example.booking.view.BookingView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	private final BookingService bookingService;
	private final RestClient restClient;

	public BookingController(BookingService bookingService, RestClient restClient) {
		this.bookingService = bookingService;
		this.restClient = restClient;
	}

	@PostMapping
	public ResponseEntity<BookingView> bookHotel(@RequestBody BookingRequest bookingRequest, Authentication user) {
		String userId = getUserId(user.getName());
		BookingView bookingView = bookingService.book(userId, bookingRequest.hotelId(), bookingRequest.rooms());
		return ResponseEntity.ok(bookingView);
	}

	@GetMapping("/{bookingId}/receipt")
	public ResponseEntity<String> downloadReceipt(@PathVariable String bookingId) {
		String recipt = bookingService.getRecipt(bookingId);
		return new ResponseEntity<>(recipt, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<BookingDetailsView>> listHotels(Authentication user) {
		String userId = getUserId(user.getName());
		List<Booking> bookings = bookingService.getBookings(userId);
		List<BookingDetailsView> bookingList = bookings.stream().map(Booking::project).toList();
		return ResponseEntity.ok(bookingList);
	}

	private String getUserId(String name) {
		return restClient.get()
				.uri("/internal/users/{name}", name)
				.retrieve()
				.body(String.class);
	}
}
