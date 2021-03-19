package tourguide.service.location;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gpsUtil.GpsUtil;
import tourguide.service.location.helper.InternalTestHelper;
import tourguide.service.location.thread.ThreadUserService;

public class TestPerformance {

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

	@DisplayName("Track the user to get his location for an high volume of users.")
	@Test
	public void highVolumeTrackLocation() {
		ThreadUserService threadUserService = new ThreadUserService();
		StopWatch stopWatch = new StopWatch();

		InternalTestHelper.setInternalUserNumber(10);
// Users should be incremented up to 100,000, and test finishes within 15 minutes
// Note : the amount of users tracked depends of the number of users set and the amount of threads used.
		threadUserService.setThreadAmount(1000);

		System.out.format("Number of user's locations to track : %d \n",
				threadUserService.getThreadAmount() * InternalTestHelper.getInternalUserNumber());

		stopWatch.start();
		threadUserService.trackUsersLocationsThreadPool();
		stopWatch.stop();

		System.out.format("highVolumeTrackLocation: Time Elapsed: %d seconds.",
				TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

}
