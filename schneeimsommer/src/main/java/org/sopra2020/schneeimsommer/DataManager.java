package org.sopra2020.schneeimsommer;

import com.bc.ceres.glevel.MultiLevelImage;
import com.google.common.primitives.Floats;
import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.Product;
import java.awt.*;
import java.awt.image.Raster;
import java.io.IOException;

public class DataManager
{
    private String inputPath;
    private Product product;
    private float allmax;
    private float allmin;
    private int[][] PixInt;

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
     *
     * @param rectangle
     * @return filtered product data by the rectangleframe
     */
    public float[][] extractData(Rectangle rectangle) throws IOException {
        Band band = product.getBand("Amplitude_VH"); //Anpassen für Funktionserweiterungen
        // MultiLevelImage image = band.getGeophysicalImage();
        // Raster raster = band.getData(rectangle);

        float[][]pixfl= new float[rectangle.height][rectangle.width];
         allmax = 0;
         allmin = 10000;
        float[] Pixels = new float[rectangle.width];
        
        System.out.println("Raster Height:"+band.getRasterHeight()+" RasterWidth:"+band.getRasterWidth());
        for(int i =0;i<rectangle.height;i++) {
            try {
                System.out.println(
                        "x:" + rectangle.x
                        +" y:" + (rectangle.y+rectangle.height-i)
                        +" width:"+rectangle.width
                        +" height:"+ 1
                );


                System.out.println(Floats.max(Pixels) + " " + Floats.min(Pixels));
                // Sollten wir hier band.getPixels() nutzen wie in Output.java?
                pixfl[i] = band.readPixels(rectangle.x, rectangle.y+rectangle.height-i, rectangle.width, 1, Pixels);
                
                if (allmin > Floats.min(pixfl[i])) {
                    allmin = Floats.min(Pixels);
                }
                if (allmax < Floats.max(Pixels)) {
                    allmax = Floats.max(Pixels);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Produkt schließen (graceful) damit es woanders neu geöffnet werden kann
            product.closeIO();
            product.dispose();
        }


        byte[][] PixByt = new byte[pixfl.length][pixfl[0].length];

        PixInt = new int[pixfl.length][pixfl[1].length];
        for(int j=0;j<pixfl.length;j++) {
            for (int i = 0; i < pixfl[0].length; i++) {
                PixByt[j][pixfl[0].length - i - 1] = (byte) ((pixfl[j][i] - allmin) / (allmax - allmin) * 255);
                PixInt[j][pixfl[0].length - i - 1] = (int) ((pixfl[j][i] - allmin) / (allmax - allmin) * 255);
            }
        }


        System.err.println(allmax + "  " + allmin);
        
        
        return pixfl;
    }
    public void exit(){

        // Produkt schließen (graceful) damit es woanders neu geöffnet werden kann
        try {
            product.closeIO();
            product.dispose();
        }catch(Exception e){

        }
    }
}