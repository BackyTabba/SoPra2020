package org.sopra2020.schneeimsommer;

import com.bc.ceres.glevel.MultiLevelImage;
import com.google.common.primitives.Floats;
import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.Product;
import java.awt.*;
import java.awt.image.Raster;
import java.io.IOException;

// A function which manages the data

public class DataManager
{
    private String inputPath;
    private Product product;
    private float allmax;
    private float allmin;
    private int [][] pixInt;

    public DataManager (String inputPath)
    {
        this.inputPath = inputPath;
    }


    /**
     * If not initialized, it runs loadProduct (inputPath)
     * @return Product
     */

    public Product getProduct ()
    {
        if (this.product == null)
        {
            loadProduct();
        }
        return this.product;
    }


    /**
     *  Loads product out of inputPath
     */

    private void loadProduct () throws NullPointerException
    {
        try
        {
            this.product = ProductIO.readProduct(inputPath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    /**
     * A function to read the pixels of the rectangle and work with them
     * @param rectangle     The rectangle frame
     * @return filtered product data by the rectangleframe
     */

    public float [][] extractData (Rectangle rectangle) throws IOException
    {
        Band band = product.getBand ("Amplitude_VH");

        float [][] pixfl = new float [rectangle.height][rectangle.width];
        allmax = 0;
        allmin = 10000;

        for (int i = 0; i < rectangle.height; i++)
        {
            try
            {
                pixfl [i] = band.readPixels (rectangle.x, rectangle.y + rectangle.height - i, rectangle.width,1, (float []) null);
                
                if (allmin > Floats.min (pixfl [i]))
                {
                    allmin = Floats.min (pixfl [i]);
                }
                if (allmax < Floats.max (pixfl [i]))
                {
                    allmax = Floats.max(pixfl[i]);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        product.closeIO();

        byte [][] pixByt = new byte [pixfl.length][pixfl[0].length];
        pixInt = new int [pixfl.length][pixfl[1].length];

        for (int j = 0; j < pixfl.length; j++)
        {
            for (int i = 0; i < pixfl[0].length; i++)
            {
                pixByt [j][pixfl[0].length - i - 1] = (byte) ((pixfl [j][i] - allmin) / (allmax - allmin) * 255);
                pixInt [j][pixfl[0].length - i - 1] = (int) ((pixfl[j][i] - allmin) / (allmax - allmin) * 255);
            }
        }
        return pixfl;
    }


    /**
     * A function to exit a stream to a product
     */

    public void exit ()
    {
        try
        {
            product.closeIO();
            product.dispose();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}