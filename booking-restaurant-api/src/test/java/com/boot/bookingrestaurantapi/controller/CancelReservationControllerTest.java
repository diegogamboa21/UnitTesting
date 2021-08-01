package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.CancelReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.CancelReservationService;

public class CancelReservationControllerTest {
	
	private static final String LOCATOR = "Miami US";
	private static final String LOCATOR_DELETE_SUCCESS = "LOCATOR_DELETED";

	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";
	
	@Mock
	CancelReservationService cancelReservationService;
	
	@InjectMocks
	CancelReservationController cancelReservationController;
	
	@Before
	public void init() throws BookingException{
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void deleteReservationTest() throws BookingException {
		Mockito.when(cancelReservationService.deleteReservation(LOCATOR)).thenReturn(LOCATOR_DELETE_SUCCESS);
		
		BookingResponse<String> responseDelete = cancelReservationController.deleteReservation(LOCATOR);
		assertEquals(SUCCESS_STATUS, responseDelete.getStatus());
		assertEquals(SUCCESS_CODE, responseDelete.getCode());
		assertEquals(OK, responseDelete.getMessage());
		assertNotNull(responseDelete.getData());
	}

}
