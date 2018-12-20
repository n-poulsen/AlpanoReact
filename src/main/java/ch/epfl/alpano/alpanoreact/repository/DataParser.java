package ch.epfl.alpano.alpanoreact.repository;

import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.DiscreteElevationModel;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import ch.epfl.alpano.summit.Summit;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static ch.epfl.alpano.summit.GazetteerParser.readSummitsFrom;

@Repository
public class DataParser {

    /** The Continuous Elevation Model used in this application*/
    private final ContinuousElevationModel cem;

    /** The List of Summits used for the above Continuous Elevation Model*/
    private final List<Summit> summits;

    /** Constructs a new DataParser */
    public DataParser() throws IOException {
        cem = loadCEM();
        summits = readSummitsFrom(new File("alps.txt"));
    }

    /**
     * Constructs a ContinuousElevationModel based on HGT Files that covers the Swiss alps
     *
     * @return the constructed CEM
     */
    private ContinuousElevationModel loadCEM() {
        HgtDiscreteElevationModel hgt1 = new HgtDiscreteElevationModel(
                new File("N45E006.hgt"));
        HgtDiscreteElevationModel hgt2 = new HgtDiscreteElevationModel(
                new File("N45E007.hgt"));
        HgtDiscreteElevationModel hgt3 = new HgtDiscreteElevationModel(
                new File("N45E008.hgt"));
        HgtDiscreteElevationModel hgt4 = new HgtDiscreteElevationModel(
                new File("N45E009.hgt"));
        HgtDiscreteElevationModel hgt5 = new HgtDiscreteElevationModel(
                new File("N46E006.hgt"));
        HgtDiscreteElevationModel hgt6 = new HgtDiscreteElevationModel(
                new File("N46E007.hgt"));
        HgtDiscreteElevationModel hgt7 = new HgtDiscreteElevationModel(
                new File("N46E008.hgt"));
        HgtDiscreteElevationModel hgt8 = new HgtDiscreteElevationModel(
                new File("N46E009.hgt"));

        DiscreteElevationModel dem = hgt1.union(hgt2).union(hgt3).union(hgt4)
                .union(hgt5.union(hgt6).union(hgt7).union(hgt8));

        return new ContinuousElevationModel(dem);
    }

    /**
     *
     *
     * @return
     */
    public ContinuousElevationModel getCEM(){
        return cem;
    }

    /**
     *
     *
     * @return
     */
    public List<Summit> getSummits(){
        return summits;
    }

}
