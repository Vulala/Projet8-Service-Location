package tourguide.service.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourguide.service.location.service.AttractionUtility;
import tourguide.service.location.user.Coordinates;
import tourguide.service.location.user.User;

public class AttractionUtilityTest {

	private GpsUtil gpsUtil = new GpsUtil();
	private AttractionUtility attractionUtility = new AttractionUtility(new RewardCentral());
	private Attraction attraction = gpsUtil.getAttractions().get(0);

	@BeforeEach
	void setup() {
		gpsUtil = new GpsUtil();
		attractionUtility = new AttractionUtility(new RewardCentral());
	}

	@Test
	public void isWithinAttractionProximity() {
		assertTrue(attractionUtility.isWithinAttractionProximity(attraction, attraction));
	}

	@Test
	public void nearAttraction() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction, new Date()));

		attractionUtility.setDefaultProximityBuffer(20);
		attractionUtility.setProximityBuffer(150);
		boolean nearAttraction = attractionUtility.nearAttraction(user.getLastVisitedLocation(), attraction);

		assertEquals(nearAttraction, true);
	}

	@Test
	public void getRewardPoints() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Integer pointsRewarded = attractionUtility.getRewardPoints(attraction, user);

		assertSame(pointsRewarded.getClass(), Integer.class);// Because the points rewarded are random
		assertTrue(pointsRewarded > 0);
	}

	@Test
	public void givenHavingTwoCoordinates_whenGetDistance_thenItReturnTheDistanceBetweenTwoPointsInNauticalMiles() {
		Location loc1 = new Location(0.0, 0.0);
		Location loc2 = new Location(1.0, 1.0);
		double distance = attractionUtility.getDistance(loc1, loc2);

		assertEquals(distance, 97.64439545235415);
	}

	@Test
	public void givenHavingTwoCoordinates_whenGetDistanceCoordinates_thenItReturnTheDistanceBetweenTwoPointsInNauticalMiles() {
		Coordinates loc1 = new Coordinates(0.0, 0.0);
		Coordinates loc2 = new Coordinates(1.0, 1.0);
		double distance = attractionUtility.getDistanceCoordinates(loc1, loc2);

		assertEquals(distance, 97.64439545235415);
	}
}
