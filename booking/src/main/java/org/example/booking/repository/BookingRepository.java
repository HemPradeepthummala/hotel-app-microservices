package org.example.booking.repository;

import org.example.booking.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
	Booking getBookingsByBookingId(String bookingId);

	List<Booking> findBookingsByUserId(String userId);
}

