package ch.epfl.alpano.alpanoreact.service;

import ch.epfl.alpano.*;
import ch.epfl.alpano.alpanoreact.repository.DataParser;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.image.ChannelPainter;
import ch.epfl.alpano.image.ImagePainter;
import ch.epfl.alpano.summit.Summit;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import java.io.File;
import java.util.List;

import static java.lang.Math.toRadians;

@Service
public class Alpano {

    /** Standards constants used for predefined panorama */
    public final static int STANDARD_MAX_DISTANCE = 300, STANDARD_WIDTH = 1080,
            STANDARD_HEIGHT = 480, STANDARD_SAMPLING_EXPONENT = 0;

    /**  */
    @Autowired
    private DataParser data;

    /**  */
    private PanoramaComputer pComputer;

    /**  */
    private List<Summit> summits;

    /**  */
    private Panorama panorama;

    private final static PanoramaUserParameters pano = PredefinedPanoramas.Niesen;

    private final static PanoramaParameters PARAMS = pano.panoramaDisplayParameters();

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
        ContinuousElevationModel cem = data.getCEM();
        summits = data.getSummits();
        pComputer = new PanoramaComputer(cem);
    }

    /**
     *
     * @return
     */
    public int[] computePanorama() throws Exception{
        panorama = pComputer.computePanorama(PARAMS);
        return compute(panorama);
    }

    public int[] computePanorama(PanoramaUserParameters userParams) throws Exception{
        PanoramaParameters pp = userParams.panoramaDisplayParameters();
        panorama = pComputer.computePanorama(pp);
        return compute(panorama);
    }

    private int[] compute(Panorama p){
        int width = p.parameters().width();
        int height = p.parameters().height();
        ImagePainter painter = getDefaultImagePainter();

        int[] image = new int[height * width * 4];
        for (int h = 0; h < height; ++h){
            for (int w = 0; w < width; ++w){
                Color c = painter.colorAt(w, h);
                int index = (h * width * 4) + (w * 4);
                image[index] = (int) Math.round(c.getRed() * 255);
                image[index + 1] = (int) Math.round(c.getGreen() * 255);
                image[index + 2] = (int) Math.round(c.getBlue() * 255);
                image[index + 3] = 255;
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
