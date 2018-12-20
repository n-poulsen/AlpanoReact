package ch.epfl.alpano.alpanoreact.service;

import ch.epfl.alpano.Panorama;
import ch.epfl.alpano.PanoramaComputer;
import ch.epfl.alpano.PanoramaParameters;
import ch.epfl.alpano.alpanoreact.repository.DataParser;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.image.ChannelPainter;
import ch.epfl.alpano.image.ImagePainter;
import ch.epfl.alpano.summit.Summit;

import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

import java.util.List;

@Service
public class Alpano {

    /**  */
    @Autowired
    private DataParser data;

    /**  */
    private PanoramaComputer pComputer;

    /**  */
    private ContinuousElevationModel cem;

    /**  */
    private List<Summit> summits;

    /**  */
    private Panorama panorama;

    /**
     *
     */
    public Alpano(){

    }

    /**
     *
     */
    @PostConstruct
    private void loadData(){
        cem = data.getCEM();
        summits = data.getSummits();
        pComputer = new PanoramaComputer(cem);
    }

    /**
     *
     * @param params
     * @param imageWidth
     * @param imageHeight
     * @return
     */
    public byte[][][] computePanorama(PanoramaParameters params, int imageWidth, int imageHeight){
        panorama = pComputer.computePanorama(params);
        ImagePainter painter = getDefaultImagePainter();
        byte[][][] image = new byte[imageHeight][imageWidth][4];
        for (int h = 0; h < imageHeight; ++h){
            for (int w = 0; w < imageWidth; ++w){
                Color c = painter.colorAt(w, h);
                image[h][w][0] = (byte) Math.round(c.getRed() * 255);
                image[h][w][1] = (byte) Math.round(c.getGreen() * 255);
                image[h][w][2] = (byte) Math.round(c.getBlue() * 255);
                image[h][w][3] = 1;
            }
        }
        return image;
    }

    /**
     *
     * @return
     */
    public List<Summit> getSummits(){
        return summits;
    }

    /**
     * Returns the most recent panorama computed
     *
     * @return the Panorama
     */
    private Panorama getPanorama() {
        return panorama;
    }

    /**
     * Generates a default ImagePainter to create the image for this
     *
     * @return the default ImagePainter
     */
    private ImagePainter getDefaultImagePainter() {
        ChannelPainter distance = getPanorama()::distanceAt;
        ChannelPainter slope = getPanorama()::slopeAt;
        ChannelPainter hue = distance.div(100000).cycling().mul(360);
        ChannelPainter saturation = distance.div(200000).clamped().inverted();
        ChannelPainter brightness = slope.mul(2).div((float) Math.PI).inverted()
                .mul(0.7f).add(0.3f);
        ChannelPainter opacity = distance
                .map(d -> d == Float.POSITIVE_INFINITY ? 0 : 1);

        return ImagePainter.hsb(hue, saturation, brightness, opacity);
    }


}
