package org.sopra2020.schneeimsommer;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.GeoCoding;
import org.esa.snap.core.datamodel.GeoPos;
import org.esa.snap.core.datamodel.PixelPos;
import org.esa.snap.core.datamodel.Product;
import java.io.IOException;

public class Geocordinates {
    /**
     * Gets and dumps out geo-coding information.
     * It shows how to convert from pixel coordinates to geo coordinates and back.
     * Note that you can import the output of this test program in BEAM application as transect profile.
     */
    public static PixelPos reference(Product product, GeoPos geoPos)
    {
        GeoCoding geoCoding = product.getSceneGeoCoding();
        return geoCoding.getPixelPos(geoPos, null);
    }
}