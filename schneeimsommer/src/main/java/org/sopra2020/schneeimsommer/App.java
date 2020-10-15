package org.sopra2020.schneeimsommer;

import com.bc.ceres.glevel.MultiLevelImage;
import com.google.common.primitives.Floats;
import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.GeoPos;
import org.esa.snap.core.datamodel.Product;

import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

// The main function to use the programm

public class App
{
    public static void main (String[] args)
    {
        try
        {
            // The input of the area of interest frame coordinates
            AreasOfInterest aoi = new AreasOfInterest ("frame", new GeoPos (50.119167,-122.896667), new GeoPos (50.105556,-122.958333), new GeoPos (50.114582,-122.934737), new GeoPos(50.113502,-122.937033));

            // The summerdata
            DataManager SOdm = new DataManager ("C:\\temp\\S1B_IW_GRDH_1SDV_20200724T142022_20200724T142047_022614_02AEB8_6071.zip");
            Geocoordinates SOgc = new Geocoordinates (SOdm.getProduct());
            Rectangle SOrectRef = SOgc.createRectangle (aoi.getGeoposRef1(), aoi.getGeoposRef2());
            Rectangle SOrectSki = SOgc.createRectangle (aoi.getGeoposSki1(), aoi.getGeoposSki2());

            float [][] SOdataSki = SOdm.extractData (SOrectSki);
            float [][] SOdataRef = SOdm.extractData (SOrectRef);

            //The winterdata
            DataManager WIdm = new DataManager ("C:\\temp\\S1B_IW_GRDH_1SDV_20200126T142017_20200126T142042_019989_025D07_5276.zip");
            Geocoordinates WIgc = new Geocoordinates (WIdm.getProduct());
            Rectangle WIrectRef = SOgc.createRectangle (aoi.getGeoposRef1(), aoi.getGeoposRef2());
            Rectangle WIrectSki = SOgc.createRectangle (aoi.getGeoposSki1(), aoi.getGeoposSki2());

            float[][] WIdata1 = WIdm.extractData(WIrectSki);
            float[][] WIdata2 = WIdm.extractData(WIrectRef);

            // Analyzing the data and creating the output
            Analyser surrounding = new Analyser(WIdata1, SOdataSki, WIdata2, SOdataRef);
            Snow [][] back = surrounding.colorSnow();
            Output.writeData ("C:/temp/neu/", back);

            SOdm.exit();
            WIdm.exit();
            System.out.println ("App done!");
        }
        catch (Exception e)
        {
            System.err.println (e.toString());
            e.printStackTrace();
        }
    }


    /**
     * Function for reading the data and writing it in the output
     * @param inputPath     The input path where the Sentinel - Data is
     * @param outputPath    The output path where the end picture should be created
     * @throws IOException  In case the places or the data can't be reached
     */

    public static void writeData (String inputPath, String outputPath) throws IOException
    {
        Product product = ProductIO.readProduct (inputPath);
        Band band = product.getBand ("Amplitude_VH");
        MultiLevelImage image = band.getGeophysicalImage();
        Rectangle rechteck = new Rectangle (10550, 7380, 1000, 600);
        Raster raster = image.getData(rechteck);
        FileImageOutputStream outputStream = new FileImageOutputStream (new File (outputPath));

        float [] Pixels = new float [1000];
        float allmax = 0;
        float allmin = 10000;

        for (int i = 0; i < raster.getHeight(); i++)
        {
            raster.getPixels(10550, 7380+i, 1000, 1, Pixels);
            outputStream.writeFloat(Pixels[i]);

            if (allmin > Floats.min (Pixels))
            {
                allmin = Floats.min (Pixels);
            }
            if (allmax < Floats.max (Pixels))
            {
                allmax = Floats.max (Pixels);
            }
        }
    }


    /**
     * Calculates the average of the data
     * @param f     The data where the average should be calculated
     * @return      The average
     */

    private float avg (Float [] f)
    {
        float sum = 0;
        for (float a : f)
        {
            sum += a;
        }
        return sum / f.length;
    }
}