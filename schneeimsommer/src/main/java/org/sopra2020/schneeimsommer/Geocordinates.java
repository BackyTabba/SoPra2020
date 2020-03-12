package org.sopra2020.schneeimsommer;

import org.esa.snap.core.datamodel.GeoCoding;
import org.esa.snap.core.datamodel.GeoPos;
import org.esa.snap.core.datamodel.PixelPos;
import org.esa.snap.core.datamodel.Product;
import java.awt.*;

public class Geocordinates
{
    /*
        @param product  The product with the Metadata of the Area of Interest (AOI)
        @param geoPos   The geoposition of the AOI
        @return PixelPos    The position of the coordinates on the picture
        @see    DataManager
     */

    public static PixelPos reference (Product product, GeoPos geoPos)
    {
        GeoCoding geoCoding = product.getSceneGeoCoding();
        return geoCoding.getPixelPos (geoPos, null);
    }


    /*
        @param product  The product with the Metadata of the Area of Interest (AOI)
        @param geopos1   One of the angles of the rectangle for calculation of the rectangle
        @param geopos2    The other one of the angles of the rectangle for calculation of the rectangle
        @return Rectangle   The calculated rectangle
    */

    public Rectangle createRectangle (Product product, GeoPos geopos1, GeoPos geopos2)
    {
        PixelPos pixpos1 = reference (product, geopos1);
        PixelPos pixpos2 = reference (product, geopos2);
        return new Rectangle ((int) pixpos1.x, (int) pixpos1.y, (int) pixpos2.x - (int) pixpos1.x, (int) pixpos2.y - (int) pixpos1.y);
    }


    /*
        @param product  The product with the Metadata of the Area of Interest (AOI)
        @param geopos1   One of the angles of the rectangle for calculation of the rectangle
        @param width    The width of the rectangle
        @param height   The height of the rectangle
        @return Rectangle   The calculated rectangle
    */

    public Rectangle createRectangle (Product product, GeoPos geopos1, int width, int height)
    {
        PixelPos pixpos1 = reference (product, geopos1);
        return new Rectangle ((int) pixpos1.x, (int) pixpos1.y, width, height);
    }


    /*
        @param pixpos1  One of the angles of the rectangle for calculation of the rectangle
        @param pixpos2  The other one of the angles of the rectangle for calculation of the rectangle
        @return Rectangle   The calculated rectangle
    */

    public Rectangle createRectangle (PixelPos pixpos1, PixelPos pixpos2)
    {
        return new Rectangle ((int) pixpos1.x, (int) pixpos1.y, (int) pixpos2.x - (int) pixpos1.x, (int) pixpos2.y - (int) pixpos1.y);
    }


    /*
        @param product  The product with the Metadata of the Area of Interest (AOI)
        @param pixpos1   One of the angles of the rectangle for calculation of the rectangle
        @param width    The width of the rectangle
        @param height   The height of the rectangle
        @return Rectangle   The calculated rectangle
    */

    public Rectangle createRectangle (PixelPos pixpos1, int width, int height)
    {
        return new Rectangle ((int) pixpos1.x, (int) pixpos1.y, width, height);
    }
}