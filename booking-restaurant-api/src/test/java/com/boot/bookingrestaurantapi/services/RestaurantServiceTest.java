package com.boot.bookingrestaurantapi.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Board;
import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.services.impl.RestaurantServiceImpl;

public class RestaurantServiceTest {
	
	private static final Long RESTAURANT_ID = 1L;
	private static final String NAME = "OK";
	private static final String DESCRIPTION = "Hamburgueseria";
	private static final String ADDRESS = "Chapinero 7ma";
	private static final String IMAGE = "https://www.udemy.com/course/image.png";
	private static final List<Turn> TURN_LIST = new ArrayList<>();
	private static final List<Board> BOARDS = new ArrayList<>();
	private static final List<Reservation> RESERVATIONS = new ArrayList<>();
	
	public static final Restaurant RESTAURANT = new Restaurant();
	
	@Mock
	RestaurantRepository restaurantRepository;
	
	@InjectMocks
	RestaurantServiceImpl restaurantServiceImpl;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setName(NAME);
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRESS);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);
		RESTAURANT.setBoards(BOARDS);
		RESTAURANT.setReservations(RESERVATIONS);
	}
	
	@Test
	public void getRestaurantByIdtest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));	
		RestaurantRest restaurant = restaurantServiceImpl.getRestaurantById(RESTAURANT_ID);
		assertNotNull(restaurant);
	}
	
	@Test(expected = BookingException.class)
	public void getRestaurantByIdNotFoundTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());
		restaurantServiceImpl.getRestaurantById(RESTAURANT_ID);
		fail();
	}
	
	@Test
	public void getRestaurantsTest() throws BookingException {
		Mockito.when(restaurantRepository.findAll()).thenReturn(Arrays.asList(RESTAURANT));
		List<RestaurantRest> responseRestaurants = restaurantServiceImpl.getRestaurants();
		assertNotNull(responseRestaurants);
		assertFalse(responseRestaurants.isEmpty());
		assertEquals(1, responseRestaurants.size());
	}

}
