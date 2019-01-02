package ch.epfl.alpano;

import java.util.Objects;

import static java.lang.Math.*;

/**
 * Represents the parameters of a panorama based on the view of the user of the application
 *
 * @author Niels Poulsen (270494)
 */
public final class PanoramaUserParameters {

    protected static final double INTEGER_TO_LONGITUDE = 10000d;
    
    protected static final double INTEGER_TO_LATITUDE = 10000d;

    protected static final int KILOMETERS_TO_METERS = 1000;

    private final int observerLongitude;

    private final int observerLatitude;

    private final int observerElevation;

    private final int centerAzimuth;

    private final int horizontalFieldOfView;

    private final int maxDistance;

    private final int width;

    private final int height;

    private final int superSamplingExponent;

    /**
     * Creates a new set of user parameters needed to create a panorama
     * 
     * @param observerLongitude
     *            the longitude of the observer
     * @param observerLatitude
     *            the latitude of the observer
     * @param observerElevation
     *            the observers elevation, in meters
     * @param centerAzimuth
     *            the azimuth in the middle of the observers view, in radians
     * @param horizontalFieldOfView
     *            the angle representing how wide the observers view is, in
     *            radians
     * @param maxDistance
     *            the maximum distance the observer can see, in kilometers
     * @param width
     *            the width of the panorama that will be drawn, in pixels
     * @param height
     *            the height of the panorama that will be drawn, in pixels
     * @param superSamplingExponent
     *            the supersampling exponents value
     */
    public PanoramaUserParameters(String observerLongitude, String observerLatitude,
                                  String observerElevation, String centerAzimuth, String horizontalFieldOfView,
                                  String maxDistance, String width, String height, String superSamplingExponent) {
        this.observerLongitude = Integer.parseInt(observerLongitude);
        this.observerLatitude = Integer.parseInt(observerLatitude);
        this.observerElevation = Integer.parseInt(observerElevation);
        this.centerAzimuth = Integer.parseInt(centerAzimuth);
        this.horizontalFieldOfView = Integer.parseInt(horizontalFieldOfView);
        this.maxDistance = Integer.parseInt(maxDistance);
        this.width = Integer.parseInt(width);
        this.height = Integer.parseInt(height);
        this.superSamplingExponent = Integer.parseInt(superSamplingExponent);
    }

    /**
     * Gives the observers longitude in this
     * 
     * @return the observers longitude
     */
    public int observerLongitude() {
        return observerLongitude;
    }

    /**
     * Gives the observers latitude in this
     * 
     * @return the observers latitude
     */
    public int observerLatitude() {
        return observerLatitude;
    }

    /**
     * Gives the observers elevation in this
     * 
     * @return the observers elevation
     */
    public int observerElevation() {
        return observerElevation;
    }

    /**
     * Gives the center azimuth in this
     * 
     * @return the center azimuth
     */
    public int centerAzimuth() {
        return centerAzimuth;
    }

    /**
     * Gives the horizontal field of view in this
     * 
     * @return the horizontal field of view
     */
    public int horizontalFieldOfView() {
        return horizontalFieldOfView;
    }

    /**
     * Gives the maximum distance the observer can see in this
     * 
     * @return the maximum distance the observer can see
     */
    public int maxDistance() {
        return maxDistance;
    }

    /**
     * Gives the width of the panorama in this
     * 
     * @return the width of the panorama
     */
    public int width() {
        return width;
    }

    /**
     * Gives the height of the panorama in this
     * 
     * @return the height of the panorama
     */
    public int height() {
        return height;
    }

    /**
     * Gives the supersampling exponent in this
     * 
     * @return the supersampling exponent
     */
    public int superSamplingExponent() {
        return superSamplingExponent;
    }

    /**
     * Gives a new set of panorama parameters (representing how the panorama
     * will be calculated) based on this set of user panorama parameters
     * 
     * @return a new set of panorama parameters based on this
     */
    public PanoramaParameters panoramaParameters() {
        return new PanoramaParameters(
                new GeoPoint(toRadians(observerLongitude() / INTEGER_TO_LONGITUDE),
                        toRadians(observerLatitude() / INTEGER_TO_LATITUDE)),
                observerElevation(), toRadians(centerAzimuth()),
                toRadians(horizontalFieldOfView()), maxDistance() * KILOMETERS_TO_METERS,
                (int) pow(2, superSamplingExponent()) * width(),
                (int) pow(2, superSamplingExponent()) * height());
    }

    /**
     * Gives a new set of panorama parameters (representing how the panorama
     * will be displayed) based on this set of user panorama parameters
     * 
     * @return a new set of panorama parameters based on this
     */
    public PanoramaParameters panoramaDisplayParameters() {
        return new PanoramaParameters(
                new GeoPoint(toRadians(observerLongitude() / INTEGER_TO_LONGITUDE),
                        toRadians(observerLatitude() / INTEGER_TO_LATITUDE)),
                observerElevation(), toRadians(centerAzimuth()),
                toRadians(horizontalFieldOfView()), maxDistance() * KILOMETERS_TO_METERS,
                width(), height());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof PanoramaUserParameters){
            PanoramaUserParameters p = (PanoramaUserParameters) other;
            return this.observerLongitude == p.observerLongitude &&
                    this.observerLatitude == p.observerLatitude &&
                    this.observerElevation == p.observerElevation &&
                    this.centerAzimuth == p.centerAzimuth &&
                    this.horizontalFieldOfView == p.horizontalFieldOfView &&
                    this.maxDistance == p.maxDistance &&
                    this.width == p.width &&
                    this.height == p.height &&
                    this.superSamplingExponent == p.superSamplingExponent;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(observerLongitude, observerLatitude, observerElevation,
                centerAzimuth, horizontalFieldOfView, maxDistance, width, height, superSamplingExponent);
    }

}
