package ch.epfl.alpano;

/**
 * Represents a collection of predefined instances of user panorama parameters
 *
 * @author Ghali Chraibi (262251)
 * @author Niels Poulsen (270494)
 */

public interface PredefinedPanoramas {

    /** Standards constants used for predefined panorama */
    public final static String STANDARD_MAX_DISTANCE = "300", STANDARD_WIDTH = "1080",
            STANDARD_HEIGHT = "480", STANDARD_SAMPLING_EXPONENT = "0";

    /**
     * Represents the user panorama parameters based on the view of the Niesen
     * mountain from the Thun lake
     */
    PanoramaUserParameters Niesen = new PanoramaUserParameters("76500", "467300",
            "600", "180", "110", STANDARD_MAX_DISTANCE, STANDARD_WIDTH,
            STANDARD_HEIGHT, STANDARD_SAMPLING_EXPONENT);

    /**
     * Represents the user panorama parameters based on the view of the Alps
     * from the Jura mountains
     */
    PanoramaUserParameters AlpesDuJura = new PanoramaUserParameters("68087",
            "470085", "1380", "162", "27", STANDARD_MAX_DISTANCE, STANDARD_WIDTH,
            STANDARD_HEIGHT, STANDARD_SAMPLING_EXPONENT);
}
