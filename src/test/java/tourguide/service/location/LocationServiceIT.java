package tourguide.service.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourguide.service.location.service.AttractionUtility;
import tourguide.service.location.service.LocationService;
import tourguide.service.location.user.User;
import tripPricer.Provider;

public class LocationServiceIT {

	private GpsUtil gpsUtil = new GpsUtil();
	private AttractionUtility attractionUtility = new AttractionUtility(new RewardCentral());
	private LocationService locationService = new LocationService(gpsUtil, attractionUtility);
	private User user = new User(UUID.randomUUID(), "jon1", "000", "jon1@tourGuide.com");

	/**
	 * Change the Locale to match the Locale defined in the jar files. <br>
	 * The use of the jar file: {@link GpsUtil} can throw a
	 * <b>NumberFormatException</b> when used with a number format (locale)
	 * different than EN/US type. <br>
	 */
	@BeforeAll
	static void setupLocale() {
		Locale.setDefault(Locale.ENGLISH);
		Locale.setDefault(Locale.UK);
	}

	@BeforeEach
	void setup() {
		gpsUtil = new GpsUtil();
		attractionUtility = new AttractionUtility(new RewardCentral());
		locationService = new LocationService(gpsUtil, attractionUtility);
	}

	@Test
	public void getUserLocation() {
		VisitedLocation visitedLocation = locationService.getUserLocation(user);

		assertEquals(visitedLocation.userId, (user.getUserId()));
	}

	@Test
	public void getTripDeals() {
		List<Provider> providers = locationService.getTripDeals(user);

		assertEquals(5, providers.size());
	}

	@Test
	public void trackUserLocation() {
		VisitedLocation visitedLocation = locationService.trackUserLocation(user);

		assertEquals(user.getUserId(), visitedLocation.userId);
	}

	@Test
	public void getNearbyAttractions() {
		Attraction attraction = gpsUtil.getAttractions().get(0);
		VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), attraction, new Date());

		List<Attraction> attractions = locationService.getNearByAttractions(visitedLocation);

		assertEquals(26, attractions.size());
	}

	@Test
	public void distanceBetweenUserAndAttraction() {
		Map<Double, String> distanceBetweenUserAndAttraction = locationService.distanceBetweenUserAndAttraction(user);
		Double firstDistance = distanceBetweenUserAndAttraction.keySet().parallelStream().collect(Collectors.toList())
				.get(0);
		Double secondDistance = distanceBetweenUserAndAttraction.keySet().parallelStream().collect(Collectors.toList())
				.get(1);
		assertTrue(firstDistance < secondDistance);
		// It show that we have calculed the distance and that the distance are sorted.
	}
}
