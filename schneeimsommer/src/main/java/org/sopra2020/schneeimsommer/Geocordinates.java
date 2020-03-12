package org.sopra2020.schneeimsommer;

import org.esa.snap.core.datamodel.GeoCoding;
import org.esa.snap.core.datamodel.GeoPos;
import org.esa.snap.core.datamodel.PixelPos;
import org.esa.snap.core.datamodel.Product;
import java.awt.*;

public class Geocordinates {
    /**
     * Gets and dumps out geo-coding information.
     * It shows how to convert from pixel coordinates to geo coordinates and back.
     * Note that you can import the output of this test program in BEAM application as transect profile.
     */
    public static PixelPos reference( Product product, GeoPos geoPos) {
        GeoCoding geoCoding = product.getSceneGeoCoding();
        return geoCoding.getPixelPos(geoPos, null);
    }
    public Rectangle createRectangle(Product product, GeoPos geopos1, GeoPos geopos2) {
        PixelPos pixpos1 = reference(product, geopos1);
        PixelPos pixpos2 = reference(product, geopos2);
        return new Rectangle((int) pixpos1.x, (int) pixpos1.y, (int) pixpos2.x - (int) pixpos1.x, (int) pixpos2.y - (int) pixpos1.y);
    }

    public Rectangle createRectangle(Product product, GeoPos geopos1, int width, int height) {
        PixelPos pixpos1 = reference(product, geopos1);
        return new Rectangle((int) pixpos1.x, (int) pixpos1.y, width, height);
    }

    public Rectangle createRectangle(PixelPos pixpos1, PixelPos pixpos2) {
        return new Rectangle((int) pixpos1.x, (int) pixpos1.y, (int) pixpos2.x - (int) pixpos1.x, (int) pixpos2.y - (int) pixpos1.y);
    }
    public Rectangle createRectangle(PixelPos pixpos1, int width, int height) {
       return new Rectangle((int) pixpos1.x, (int) pixpos1.y, width, height);
    }
}