package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.ReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.ReservationService;

public class ReservationControllerTest {
	
	public static final Long RESERVATION_ID = 1L;
	public static final ReservationRest RESERVATION_REST = new ReservationRest();
	private static final Long RESTAURANT_ID = 1L;
	private static final String LOCATOR = "Miami US";
	private static final Long PERSON = 1L;
	private static final Long TURN_ID = 1L;
	private static final Date DATE = new Date();
	
	private static final CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();	
	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";

	@Mock
	ReservationService reservationService;
	
	@InjectMocks
	ReservationController reservationController;
	
	@Before
	public void init() throws BookingException{
		MockitoAnnotations.initMocks(this);
		
		RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		RESERVATION_REST.setLocator(LOCATOR);
		RESERVATION_REST.setPerson(PERSON);
		RESERVATION_REST.setTurnId(TURN_ID);
		RESERVATION_REST.setDate(DATE);	
	}
	
	@Test
	public void getReservationByIdTest() throws BookingException {
		Mockito.when(reservationService.getReservation(RESERVATION_ID)).thenReturn(RESERVATION_REST);

		BookingResponse<ReservationRest> response = reservationController.getReservationById(RESERVATION_ID);
		assertEquals(SUCCESS_STATUS, response.getStatus());
		assertEquals(SUCCESS_CODE, response.getCode());
		assertEquals(OK, response.getMessage());
		assertNotNull(response.getData());
	}
	
	@Test
	public void createResevationTest() throws BookingException {
		Mockito.when(reservationService.createReservation(CREATE_RESERVATION_REST)).thenReturn(LOCATOR);
		
		BookingResponse<String> response = reservationController.createReservation(CREATE_RESERVATION_REST);
		assertEquals(SUCCESS_STATUS, response.getStatus());
		assertEquals(SUCCESS_CODE, response.getCode());
		assertEquals(OK, response.getMessage());
		assertNotNull(response.getData());
	}
	

}
