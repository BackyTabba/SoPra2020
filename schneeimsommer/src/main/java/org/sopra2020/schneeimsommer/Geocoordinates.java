package org.sopra2020.schneeimsommer;

import org.esa.snap.core.datamodel.GeoCoding;
import org.esa.snap.core.datamodel.GeoPos;
import org.esa.snap.core.datamodel.PixelPos;
import org.esa.snap.core.datamodel.Product;
import java.awt.*;

public class Geocoordinates
{
    private Product product;

    public Geocoordinates (Product product)
    {
        this.product = product;
    }


    /**
     * A function to return the product as the frame in form of a rectangle
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


    /**
     * A function to create a rectangle with geopositions
        @param geopos1   One of the angles of the rectangle for calculation of the rectangle
        @param geopos2    The other one of the angles of the rectangle for calculation of the rectangle
        @return Rectangle   The calculated rectangle
    */

    public Rectangle createRectangle (GeoPos geopos1, GeoPos geopos2)
    {
        PixelPos pixpos1 = reference (product, geopos1);
        PixelPos pixpos2 = reference (product, geopos2);
        return new Rectangle ((int) pixpos1.x, (int) pixpos1.y, (int) pixpos2.x - (int) pixpos1.x, (int) pixpos2.y - (int) pixpos1.y);
    }


    /**
     * A function to create a rectangle with one geoposition, the height and the width
        @param geopos1   One of the angles of the rectangle for calculation of the rectangle
        @param width    The width of the rectangle
        @param height   The height of the rectangle
        @return Rectangle   The calculated rectangle
    */

    public Rectangle createRectangle (GeoPos geopos1, int width, int height)
    {
        PixelPos pixpos1 = reference (product, geopos1);
        return new Rectangle ((int) pixpos1.x, (int) pixpos1.y, width, height);
    }


    /**
     * A function to create a rectangle with two pixel positions
        @param pixpos1  One of the angles of the rectangle for calculation of the rectangle
        @param pixpos2  The other one of the angles of the rectangle for calculation of the rectangle
        @return Rectangle   The calculated rectangle
    */

    public Rectangle createRectangle (PixelPos pixpos1, PixelPos pixpos2)
    {
        return new Rectangle ((int) pixpos1.x, (int) pixpos1.y, (int) pixpos2.x - (int) pixpos1.x, (int) pixpos2.y - (int) pixpos1.y);
    }


    /**
     * A function to create a rectangle with one pixel position, the height and the width
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