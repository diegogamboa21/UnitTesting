package com.boot.bookingrestaurantapi.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRespository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.impl.ReservationServiceImpl;

public class ReservationServiceTest {

	public static final Long RESERVATION_ID = 1L;

	public static final Long RESTAURANT_ID = 1L;
	private static final String RESTAURANT_NAME = "Hamburgueseria";
	public static final Restaurant RESTAURANT = new Restaurant();

	public static final Long TURN_ID = 1L;
	public static final String TURN_NAME = "TURN_TEST";
	public static final Turn TURN = new Turn();

	private static final CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	
	public static final String LOCATOR = RESTAURANT_NAME + TURN_ID;

	@Mock
	RestaurantRepository restaurantRepository;

	@Mock
	TurnRepository turnRepository;

	@Mock
	ReservationRespository reservationRepository;

	@InjectMocks
	ReservationServiceImpl reservationServiceImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		TURN.setName(TURN_NAME);
		
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setName(RESTAURANT_NAME);

		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);
		CREATE_RESERVATION_REST.setDate(new Date());
	}

	@Test
	public void getReservationTest() throws BookingException {
		Reservation reservation = new Reservation();
		Mockito.when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(reservation));
		ReservationRest reservationRest = reservationServiceImpl.getReservation(RESERVATION_ID);
		assertNotNull(reservationRest);
	}

	@Test(expected = BookingException.class)
	public void getReservationTestNotFoundTest() throws BookingException {
		Mockito.when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.empty());
		reservationServiceImpl.getReservation(RESERVATION_ID);
		fail();
	}

	@Test
	public void createReservationTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(Optional.of(TURN));
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN_NAME, RESTAURANT_ID))
		.thenReturn(Optional.empty());
		
		Mockito
		.when(reservationRepository.save(Mockito.any(Reservation.class)))
		.thenReturn(new Reservation());

		String reservation = reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		assertNotNull(reservation);
		assertEquals(LOCATOR, reservation);
	}

	@Test(expected = BookingException.class)
	public void createReservationNotFoundRestaurantTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}
	
	@Test(expected = BookingException.class)
	public void createReservationNotFoundTurnTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(Optional.empty());

		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}
	
	@Test(expected = BookingException.class)
	public void createReservationReservationExistsTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(Optional.of(TURN));
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN_NAME, RESTAURANT_ID))
		.thenReturn(Optional.of(new Reservation()));

		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}
	
	@Test(expected = BookingException.class)
	public void createReservationDontSaveReservationTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(Optional.of(TURN));
		Mockito
			.when(reservationRepository.findByTurnAndRestaurantId(TURN_NAME, RESTAURANT_ID))
			.thenReturn(Optional.empty());		
		Mockito.doThrow(Exception.class).when(reservationRepository).save(Mockito.any(Reservation.class));
		
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}
}
