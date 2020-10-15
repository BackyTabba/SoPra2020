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
            String winterPath = "";
            String summerPath = "";
            String outputPath = "";
            float coordinatesSkiRULat = 0f;
            float coordinatesSkiRULon = 0f;
            float coordinatesSkiLBLat = 0f;
            float coordinatesSkiLBLon = 0f;
            float coordinatesRefRULat = 0f;
            float coordinatesRefRULon = 0f;
            float coordinatesRefLBLat = 0f;
            float coordinatesRefLBLon = 0f;

            for (int i = 0; i < args.length; i++)
            {
                if (args[i].startsWith ("-winterData"))
                {
                    winterPath = args[i].substring (12);
                };
                if (args[i].startsWith ("-summerData"))
                {
                    summerPath = args[i].substring (12);
                }
                if (args[i].startsWith ("-outputData"))
                {
                    outputPath = args[i].substring (12);
                }

                if (args[i].startsWith ("-coordinatesSkiRULat"))
                {
                    coordinatesSkiRULat = Float.valueOf (args[i].substring (21));
                };
                if (args[i].startsWith ("-coordinatesSkiRULon"))
                {
                    coordinatesSkiRULon = Float.valueOf (args[i].substring (21));
                }
                if (args[i].startsWith ("-coordinatesSkiLBLat"))
                {
                    coordinatesSkiLBLat = Float.valueOf (args[i].substring (21));
                }
                if (args[i].startsWith ("-coordinatesSkiLBLon"))
                {
                    coordinatesSkiLBLon = Float.valueOf (args[i].substring (21));
                };
                if (args[i].startsWith ("-coordinatesRefRULat"))
                {
                    coordinatesRefRULat = Float.valueOf (args[i].substring (21));
                }
                if (args[i].startsWith ("-coordinatesRefRULon"))
                {
                    coordinatesRefRULon = Float.valueOf (args[i].substring (21));
                }
                if (args[i].startsWith ("-coordinatesRefLBLat"))
                {
                    coordinatesRefLBLat = Float.valueOf (args[i].substring (21));
                }
                if (args[i].startsWith ("-coordinatesRefLBLon"))
                {
                    coordinatesRefLBLon = Float.valueOf (args[i].substring (21));
                }

            }
            if (winterPath == "" || summerPath == "" || outputPath == "")
            {
                System.err.println ("Please set the path!");
                return;
            }
            if (coordinatesSkiRULat == 0f || coordinatesSkiRULon == 0f || coordinatesSkiLBLat == 0f || coordinatesSkiLBLon == 0f ||
                coordinatesRefRULat == 0f || coordinatesRefRULon == 0f || coordinatesRefLBLat == 0f || coordinatesRefLBLon == 0f)
            {
                System.err.println ("Please set the coordinates!");
                return;
            }

            // The input of the area of interest frame coordinates
            AreasOfInterest aoi = new AreasOfInterest ("frame",
                    new GeoPos (coordinatesSkiRULat, coordinatesSkiRULon),
                    new GeoPos (coordinatesSkiLBLat, coordinatesSkiLBLon),
                    new GeoPos (coordinatesRefRULat, coordinatesRefRULon),
                    new GeoPos (coordinatesRefLBLat, coordinatesRefLBLon));

            // The summerdata
            DataManager SOdm = new DataManager (summerPath);
            Geocoordinates SOgc = new Geocoordinates (SOdm.getProduct());
            Rectangle SOrectRef = SOgc.createRectangle (aoi.getGeoposRef1(), aoi.getGeoposRef2());
            Rectangle SOrectSki = SOgc.createRectangle (aoi.getGeoposSki1(), aoi.getGeoposSki2());

            float [][] SOdataSki = SOdm.extractData (SOrectSki);
            float [][] SOdataRef = SOdm.extractData (SOrectRef);

            //The winterdata
            DataManager WIdm = new DataManager (winterPath);
            Geocoordinates WIgc = new Geocoordinates (WIdm.getProduct());
            Rectangle WIrectRef = WIgc.createRectangle (aoi.getGeoposRef1(), aoi.getGeoposRef2());
            Rectangle WIrectSki = WIgc.createRectangle (aoi.getGeoposSki1(), aoi.getGeoposSki2());

            float[][] WIdata1 = WIdm.extractData(WIrectSki);
            float[][] WIdata2 = WIdm.extractData(WIrectRef);

            // Analyzing the data and creating the output
            Analyser surrounding = new Analyser (WIdata1, SOdataSki, WIdata2, SOdataRef);
            Snow [][] back = surrounding.colorSnow();
            Output.writeData (outputPath, back);

            // Putting out the textform
            // Analyser textout = new Analyser (WIdata1, SOdataSki, WIdata2, SOdataRef);
            System.out.println ("The percent of the snow in the ski area: " + surrounding.getPercentSnow ());
            System.out.println ("The percent of the snow in the reference area: " + surrounding.getPercentSnowRef ());
            System.out.println ("The quantity of the fakesnow in the ski area: " + surrounding.getQuantityFalseSnow ());
            System.out.println ("The percent of the fakesnow in the ski area: " + surrounding.getPercentFalseSnow ());

            SOdm.exit();
            WIdm.exit();
        }
        catch (Exception e)
        {
            System.err.println (e.toString());
            e.printStackTrace();
        }
    }
}