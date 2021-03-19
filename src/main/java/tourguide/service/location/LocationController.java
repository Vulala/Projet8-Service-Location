package tourguide.service.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import gpsUtil.location.VisitedLocation;
import tourguide.service.location.helper.InternalTestHelper;
import tourguide.service.location.service.LocationService;
import tourguide.service.location.user.User;
import tourguide.service.location.user.UserCoordinates;
import tourguide.service.location.user.UserNearbyAttractions;
import tourguide.service.location.user.UserPreferences;
import tripPricer.Provider;

/**
 * LocationController is the main controller of this service. <br>
 * The purpose is to calculate the user's location. <br>
 */
@RestController
public class LocationController {

	@Autowired
	private InternalTestHelper internalTestHelper;

	@Autowired
	private LocationService locationService;

	/**
	 * Get : /
	 * 
	 * @return "The list of endpoints availables"
	 */
	@GetMapping("/")
	public String index() {
		return "Endpoints availables with this service : " + "\n /getLocation?userName=internalUser0 "
				+ "\n /getNearbyAttractions?userName=internalUser0" + "\n /getAllCurrentLocations "
				+ "\n /getTripDeals?userName=internalUser0" + "\n /setUserPreferences?userName=internalUser0";
	}

	/**
	 * GET mapping to get the user's location as json. <br>
	 * 
	 * @param userName
	 * @return json
	 */
	@GetMapping("/getLocation")
	public String getLocation(@RequestParam String userName) {
		internalTestHelper.initializeTheInternalUsers();
		User user = internalTestHelper.getUser(userName);
		VisitedLocation visitedLocation = locationService.getUserLocation(user);
		return JsonStream.serialize(visitedLocation.location);
	}

	/**
	 * GET mapping to get the five closest tourist attractions from the user. <br>
	 * It return : the attractionName, the coordinates, the distance of the
	 * attraction from the user and the points rewarded by the attraction. <br>
	 * It also return the user's UUID and the user's coordinates. <br>
	 * 
	 * @param userName
	 * @return {@link UserNearbyAttractions} as json
	 */
	@GetMapping("/getNearbyAttractions")
	public UserNearbyAttractions getNearbyAttractions(@RequestParam String userName) {
		internalTestHelper.initializeTheInternalUsers();
		User user = internalTestHelper.getUser(userName);
		return locationService.fiveClosestAttractions(user);

	}

	/**
	 * GET mapping to get the most recent location of every users.
	 * 
	 * @return a json containing the UUID and the coordinates of each users.
	 */
	@GetMapping("/getAllCurrentLocations")
	public List<UserCoordinates> getAllCurrentLocations() {
		internalTestHelper.initializeTheInternalUsers();
		List<User> userList = internalTestHelper.getAllUsers();
		return locationService.getAllCurrentLocations(userList);
	}

	/**
	 * GET mapping to get the user's trip deals.
	 * 
	 * @param userName
	 * @return json
	 */
	@GetMapping("/getTripDeals")
	public String getTripDeals(@RequestParam String userName) {
		internalTestHelper.initializeTheInternalUsers();
		List<Provider> providers = locationService.getTripDeals(internalTestHelper.getUser(userName));
		return JsonStream.serialize(providers);
	}

	/**
	 * PUT mapping to set the user's preferences to refine the trip deals. <br>
	 * 
	 * @param userName
	 * @return json
	 */
	@PutMapping("/setUserPreferences")
	public String setUserPreferences(@RequestParam String userName, @RequestBody UserPreferences userPreferences) {
		internalTestHelper.initializeTheInternalUsers();
		User user = internalTestHelper.getUser(userName);
		user.setUserPreferences(userPreferences);
		internalTestHelper.addUser(user);
		return "Preferences successfully changed.";
	}
}
