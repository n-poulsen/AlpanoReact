package ch.epfl.alpano.alpanoreact.api;

import ch.epfl.alpano.PanoramaParameters;
import ch.epfl.alpano.PanoramaUserParameters;
import ch.epfl.alpano.alpanoreact.service.Alpano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * API for the application
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class PublicAPI {

    /**  */
    @Autowired
    private Alpano alps;

    /**
     * Returns an array containing the pixels for this panorama, of size 1080x480
     *
     * @return an iterable collection of all buildings in the map
     */
    @GetMapping("/getImage")
    public int[] getImage(){
        try {
            return alps.computePanorama();
        }catch (Exception E){
            return new int[0];
        }
    }

    /**
     * Returns an array containing the pixels for this panorama, of size 1080x480
     *
     * @return an iterable collection of all buildings in the map
     */
    @GetMapping("/computePanorama")
    public int[] computePanorama(@RequestParam String longitude, @RequestParam String latitude, @RequestParam String elevation,
                                 @RequestParam String azimuth, @RequestParam String fieldOfView, @RequestParam String maxDistance,
                                 @RequestParam String imageWidth, @RequestParam String imageHeight, @RequestParam String samplingExponent){
        try {
            PanoramaUserParameters params = new PanoramaUserParameters(longitude, latitude, elevation, azimuth, fieldOfView,
                    maxDistance, imageWidth, imageHeight, samplingExponent);
            return alps.computePanorama(params);
        }catch (Exception E){
            return new int[0];
        }
    }

}
