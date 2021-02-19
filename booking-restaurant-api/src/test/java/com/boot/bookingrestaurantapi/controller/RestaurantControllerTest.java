package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.boot.bookingrestaurantapi.controllers.RestaurantController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.jsons.TurnRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.RestaurantService;

public class RestaurantControllerTest {
	
	private static final Long RESTAURANT_ID = 1L;
	private static final String NAME = "OK";
	private static final String DESCRIPTION = "Hamburgueseria";
	private static final String ADDRESS = "Chapinero 7ma";
	private static final String IMAGE = "https://www.udemy.com/course/image.png";
	private static final List<TurnRest> TURN_LIST = new ArrayList<>();
	
	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";
	
	public static final RestaurantRest RESTAURANT_REST = new RestaurantRest();
	public static final List<RestaurantRest> RESTAURANT_REST_LIST = new ArrayList<>();
	
	@Mock
	RestaurantService restaurantService;
	
	@InjectMocks
	RestaurantController restaurantController;
	
	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		
		RESTAURANT_REST.setId(RESTAURANT_ID);
		RESTAURANT_REST.setName(NAME);
		RESTAURANT_REST.setDescription(DESCRIPTION);
		RESTAURANT_REST.setAddress(ADDRESS);
		RESTAURANT_REST.setImage(IMAGE);
		RESTAURANT_REST.setTurns(TURN_LIST);
		
		RESTAURANT_REST_LIST.add(RESTAURANT_REST);
	}
	
	@Test
	public void getRestaurantByIdTest() throws BookingException {
		Mockito.when(restaurantService.getRestaurantById(RESTAURANT_ID)).thenReturn(RESTAURANT_REST);
		final BookingResponse<RestaurantRest> response = restaurantController.getRestaurantById(RESTAURANT_ID);
		
		assertEquals(SUCCESS_STATUS, response.getStatus());
		assertEquals(SUCCESS_CODE, response.getCode());
		assertEquals(OK, response.getMessage());
		assertEquals(RESTAURANT_REST, response.getData());
	}
	
	@Test
	public void getRestaurantsTest() throws BookingException {
		Mockito.when(restaurantService.getRestaurants()).thenReturn(RESTAURANT_REST_LIST);
		final BookingResponse<List<RestaurantRest>> response = restaurantController.getRestaurants();
		
		assertEquals(SUCCESS_STATUS, response.getStatus());
		assertEquals(SUCCESS_CODE, response.getCode());
		assertEquals(OK, response.getMessage());
		assertFalse(response.getData().isEmpty());
		assertEquals(RESTAURANT_REST_LIST, response.getData());
	}

}
