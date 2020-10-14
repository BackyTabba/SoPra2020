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

public class App
{

     /*   Product product = null;
        System.out.println ("Baender:" + product.getBandNames());
        try
        {
            product = ProductIO.readProduct("test");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }*/

        public static void main(String[] args)
        {
            try {
                AreasOfInterest aoi = new AreasOfInterest("test",new GeoPos(47.317992,11.862789),new GeoPos(47.315432,11.854120), new GeoPos(47.268126,11.851987 ), new GeoPos(47.265331, 11.828469));

                //Sommerbild
                // Datamanager
                DataManager SOdm = new DataManager("C:\\temp\\S1B_20200711.zip");
                Geocoordinates SOgc = new Geocoordinates(SOdm.getProduct());

                Rectangle SOrect = SOgc.createRectangle(aoi.getGeoposRef1(), aoi.getGeoposRef2());
                System.out.println(SOrect);
                float[][] SOdata1 = SOdm.extractData(SOrect);
                float[][] SOdata2 = SOdm.extractData(SOrect);


                //Winterbild
                // Datamanager
                DataManager WIdm = new DataManager("C:\\temp\\S1B_20200206.zip");
                Geocoordinates WIgc = new Geocoordinates(WIdm.getProduct());
                Rectangle WIrect = WIgc.createRectangle(aoi.getGeoposRef1(), aoi.getGeoposRef2());
                float[][] WIdata1 = WIdm.extractData(WIrect);
                float[][] WIdata2 = WIdm.extractData(WIrect);


                //Analyze Data

                Analyser Umgebung1 = new Analyser(WIdata1, SOdata1, WIdata2, SOdata2);
                Snow[][]rueck =Umgebung1.colorSnow();
                Output.writeData("C:/temp/neu/",rueck);
                //Umgebung1.clean(Umgebung1.getQuantitySnow(SOdata1),Umgebung1.getQuantitySnow(SOdata2));

            }catch(Exception e){
                System.err.println(e.toString());
                e.printStackTrace();

            }

         /*  if (args.length < 2)
           {
                System.out.println("parameter usage: <input-file> <output-file>");
                return;
            }*/

            // Get arguments
            /*
            String inputPath = "C:\\Users\\adria\\Downloads\\S1A_IW_GRDH_1SDV_2019.07.31_SOMMER.zip";
            String outputPath = "C:\\temp\\test";

            try
            {
                // Pass arguments to actual program code
                writeData(inputPath, outputPath);
            }
            catch (IOException e)
            {
                System.out.println("error: " + e.getMessage());
            }*/
        }

        public static void writeData(String inputPath,String outputPath)throws IOException {
            Product product = ProductIO.readProduct(inputPath);
            Band band = product.getBand("Amplitude_VH");
            MultiLevelImage image = band.getGeophysicalImage();
            Rectangle rechteck = new Rectangle(10550, 7380, 1000, 600);
            Raster raster = image.getData(rechteck);
            FileImageOutputStream outputStream = new FileImageOutputStream(new File(outputPath));
            float[] Pixels = new float[1000];//länge

            //For each pixelreihe

           // float allavg=Pixels[];
            float allmax=0;
            float allmin=10000;
            for (int i = 0; i < raster.getHeight(); i++) {


                raster.getPixels(10550, 7380+i, 1000, 1, Pixels);
              //  outputStream.writeByte(Pixels[i]);
                outputStream.writeFloat(Pixels[i]);
                //System.out.println(Arrays.toString(Pixels));#

                if(allmin>Floats.min(Pixels)){
                    allmin=Floats.min(Pixels);
                }
                if(allmax<Floats.max(Pixels)){
                    allmax=Floats.max(Pixels);
                }
                System.out.println(Floats.max(Pixels)+" "+Floats.min(Pixels));

               // Arrays.stream(Pixels).average().orElse(Double.NaN);

            }
            System.err.println(allmax+"  "+allmin);
            

        }
        private float avg(Float[] f){
            float sum=0;
        for(float a : f){
            sum+=a;
        }
        return sum/f.length;
        }

        /*private static void run(String inputPath, String outputPath)
                throws IOException
        {
            Product product = ProductIO.readProduct(inputPath);

        // Get the "high" band
        Band hiBand = product.getBand("radiance_10");

        if (hiBand == null)
        {
            throw new IOException("hi-band 'radiance_10' not found");
        }

            // Get the "low" band
            Band lowBand = product.getBand("radiance_6");
            int hier = product.getBandNames().length;
            String hier2 = Arrays.toString(product.getBandNames());
            product.getSceneRasterHeight();
            product.getSceneRasterWidth();


            // Get the "high" band
            Band hiBand = product.getBand("Amplitude_VH");
            MultiLevelImage testimage = hiBand.getGeophysicalImage();
           // ProductData testdaten = hiBand.getData();
            Rectangle testrechteck = new Rectangle(10550, 7380,1000,600);
            Raster testraster = hiBand.getGeophysicalImage().getData(testrechteck);
            int width = testraster.getWidth();
            int heigth = testraster.getHeight();
            int minX = testraster.getMinX();
            int minY = testraster.getMinY();


            String bla = testraster.toString();

            int i=0;
            Rectangle testrectangle = testraster.getBounds();
            int a = testrectangle.height;
            int b = testrectangle.width;
            System.out.println("b = " + b);
            System.out.println("a = " + a);

        // Create a buffer for reading a single scan line of the hi-band
        float[] hiBandPixels = new float[w];

        // Hi/Low-band sum and difference of the NDVI quotient
        float sum, dif;

        // NDVI value
        float ndvi;

        // NDVI value in the range 0 to 255
        int ndviByte;

        // For all scan lines in the product...
        for (int y = 0; y < h; y++)
        {
            // Read low-band pixels for line y
            lowBand.readPixels(0, y, w, 1, lowBandPixels, new PrintWriterProgressMonitor(System.out));

            // Read hi-band pixels for line y
            hiBand.readPixels(0, y, w, 1, hiBandPixels, new PrintWriterProgressMonitor(System.out));

            // Compute NDVI for all x
            for (int x = 0; x < w; x++)
            {
                dif = lowBandPixels[x] - hiBandPixels[x];
                sum = lowBandPixels[x] + hiBandPixels[x];

                if (sum != 0.0F)
                {
                    ndvi = dif / sum;
                }

                else
                {
                    ndvi = 0.0F;
                }

                if (ndvi < 0.0F)
                {
                    ndvi = 0.0F;
                }

                else if (ndvi > 1.0F)
                {
                    ndvi = 1.0F;
                }

                // Convert NDVI to integer in the range 0 to 255
                ndviByte = (int) (255 * ndvi);

                // write NDVI byte to raw image file
                outputStream.writeByte(ndviByte);
            }
        }

        // close raw image file
        outputStream.close();

        // Done!
        System.out.println("OK");
    }*/
}