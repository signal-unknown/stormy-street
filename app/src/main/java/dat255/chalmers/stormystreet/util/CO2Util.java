package dat255.chalmers.stormystreet.util;

/**
 * CO2 emissions are based on statistics from the following article:
 * <a href="http://www.acea.be/industry-topics/tag/category/co2-from-cars-and-vans">CO2 from Cars and Vans</a>
 * http://www.acea.be/industry-topics/tag/category/co2-from-cars-and-vans
 *
 * @author Alexander Håkansson
 */
public class CO2Util {

    public enum CO2EmissionRegion {
        EU, JAPAN, CHINA, AMERICA, ALL;

        private long co2PerKm;

        static {
            EU.co2PerKm = 130;
            JAPAN.co2PerKm = 125;
            CHINA.co2PerKm = 170;
            AMERICA.co2PerKm = 180;
            ALL.co2PerKm = 151; // Average
        }
    }

    private static CO2EmissionRegion staticRegion = CO2EmissionRegion.ALL;

    public static long getGramsSavedPerKm(long km) {
        return km * staticRegion.co2PerKm;
    }

    public static long getGramsSavedPerKm(long km, CO2EmissionRegion region) {
        return km * region.co2PerKm;
    }

    public static void setRegionToUse(CO2EmissionRegion region) {
        staticRegion = region;
    }
}
