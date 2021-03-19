package tourguide.service.location;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import tourguide.service.location.helper.InternalTestHelper;
import tourguide.service.location.service.LocationService;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LocationService locationService;

	@Autowired
	private InternalTestHelper internalTestHelper;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(locationService).isNotNull();
		assertThat(internalTestHelper).isNotNull();
	}

	@DisplayName("GET : /")
	@Test
	void index() throws Exception {
		// ACT
		MvcResult mvcResult = this.mockMvc.perform(get("/")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("GET : /getLocation")
	@Test
	void givenGettingTheLocationOfTheUser_whenGetLocation_thenItDisplayTheCoordinateOfTheUserAsJSON() throws Exception {
		// ACT
		MvcResult mvcResult = this.mockMvc.perform(get("/getLocation?userName=internalUser0")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("GET : /getNearbyAttractions")
	@Test
	void givenGettingTheFiveClosestAttrationsOfTheUser_whenGetNearbyAttraction_thenItDisplayTheRequestedDataForTheUserAsJSON()
			throws Exception {
		// ACT
		MvcResult mvcResult = this.mockMvc.perform(get("/getNearbyAttractions?userName=internalUser0")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("GET : /getAllCurrentLocations")
	@Test
	void givenGettingAllCurrentLocations_whenGetAllCurrentLocations_thenItDisplayTheLocationOfEveryUsers()
			throws Exception {
		// ACT
		MvcResult mvcResult = this.mockMvc.perform(get("/getAllCurrentLocations")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);

	}

	@DisplayName("GET : /getTripDeals")
	@Test
	void givenGettingTheTripDealsOfTheUser_whenGetTripDeals_thenItDisplayTheTripDealsOfTheUserAsJSON()
			throws Exception {
		// ACT
		MvcResult mvcResult = this.mockMvc.perform(get("/getTripDeals?userName=internalUser0")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("PUT : /setUserPreferences")
	@Test
	void givenSettingTheUserPreferences_whenSetUserPreferences_thenItCorrectlyChangeTheUserPreferencesWithTheProvidedData()
			throws Exception {
		// ACT
		MvcResult mvcResult = this.mockMvc.perform(put("/setUserPreferences?userName=internalUser0")
				.contentType(MediaType.APPLICATION_JSON).content("{\"attractionProximity\":\"0\"}")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}
}
