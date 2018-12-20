package ch.epfl.alpano.alpanoreact.service;

import ch.epfl.alpano.PanoramaParameters;
import ch.epfl.alpano.alpanoreact.repository.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Alpano {

    /**  */
    @Autowired
    private DataParser data;

    /**
     *
     */
    public Alpano(){

    }

    /**
     *
     * @return
     */
    public byte[] computePanorama(PanoramaParameters params){

        return new byte[0];
    }
}
