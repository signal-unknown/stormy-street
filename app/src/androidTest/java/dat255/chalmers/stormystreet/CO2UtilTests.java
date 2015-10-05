package dat255.chalmers.stormystreet;

import android.test.ActivityTestCase;

import dat255.chalmers.stormystreet.utilities.CO2Util;

/**
 * @author Alexander Håkansson
 */
public class CO2UtilTests extends ActivityTestCase {

    public void testDefaultSettingIsALL() {
        long km = 100;
        long expected = km * 151;
        assertEquals("Util should default to region ALL", expected, CO2Util.getGramsSavedPerKm(km));
    }

    public void testRegionIsSaved() {
        CO2Util.setRegionToUse(CO2Util.CO2EmissionRegion.EU);
        long km = 100;
        long expected = km * 130;
        assertEquals("Region should be saved to EU", expected, CO2Util.getGramsSavedPerKm(km));
    }

    public void testActuallyUsingDefinedRegion() {
        CO2Util.setRegionToUse(CO2Util.CO2EmissionRegion.ALL);
        long km = 100;
        long expected = km * 130;
        assertEquals("The provided region should be used over the default one", expected, CO2Util.getGramsSavedPerKm(km, CO2Util.CO2EmissionRegion.EU));
    }
}
