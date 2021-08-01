package com.boot.bookingrestaurantapi.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.repositories.ReservationRespository;
import com.boot.bookingrestaurantapi.services.impl.CancelReservationServiceImpl;

public class CancelReservationServiceTest {
	
	public static final String LOCATOR = "Hamburgueseria1";
	public static final String STATUS_CANCEL_RIGHT = "LOCATOR_DELETED";
	
	@Mock
	ReservationRespository reservationRespository;
	
	@InjectMocks
	CancelReservationServiceImpl cancelReservationServiceImpl;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void deleteReservationTest() throws BookingException {
		Reservation reservation = new Reservation();
		Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(Optional.of(reservation));
		
		Mockito.when(reservationRespository.save(Mockito.any(Reservation.class))).thenReturn(reservation);
		
		String canceledReservation = cancelReservationServiceImpl.deleteReservation(LOCATOR);
		assertNotNull(canceledReservation);
		assertEquals(STATUS_CANCEL_RIGHT, canceledReservation);
	}
	
	@Test(expected = BookingException.class)
	public void deleteReservationNotFoundTest() throws BookingException {
		Reservation reservation = new Reservation();
		Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(Optional.empty());
		
		Mockito
			.when(reservationRespository.deleteByLocator(Mockito.any(String.class)))
			.thenReturn(Optional.of(reservation));
		
		cancelReservationServiceImpl.deleteReservation(LOCATOR);
		fail();
	}
	
	@Test(expected = BookingException.class)
	public void deleteReservationExceptionTest() throws BookingException {
		Reservation reservation = new Reservation();
		Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(Optional.of(reservation));
		Mockito
			.doThrow(Exception.class)
			.when(reservationRespository).deleteByLocator(Mockito.any(String.class));
				
		cancelReservationServiceImpl.deleteReservation(LOCATOR);
		fail();
	}

}
