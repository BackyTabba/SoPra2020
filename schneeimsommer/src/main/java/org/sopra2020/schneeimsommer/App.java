package org.sopra2020.schneeimsommer;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;
import java.io.IOException;

import com.bc.ceres.core.PrintWriterProgressMonitor;
import org.esa.snap.core.datamodel.Band;
import javax.imageio.stream.FileImageOutputStream;
import java.io.File;

public class App
{
  /*  public static void main (String[] args)
    {
        Product product = null;
        system.out.println ("Baender:" + product.getBandNames());
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
            String inputPath = "C:/Users/Tatjana/Documents/S1A_IW_GRDH_1SDV_20191224T054207_20191224T054232_030486_037D8C_8E6A.zip";
            String outputPath = "C:/Users/Tatjana/Desktop/Neues_Bild";

            try
            {
                // Pass arguments to actual program code
                run(inputPath, outputPath);
            }
            catch (IOException e)
            {
                System.out.println("error: " + e.getMessage());
            }
        }

        private static void run(String inputPath, String outputPath)
                throws IOException
        {
            // Read the product (note that only 'nodes' are read, not the entire data!)
            Product product = ProductIO.readProduct(new File(inputPath));

            // Get the scene width
            int w = product.getSceneRasterWidth();

            // Get the scene height
            int h = product.getSceneRasterHeight();


            // Get the "low" band
            Band lowBand = product.getBand("radiance_6");

            if (lowBand == null)
            {
                throw new IOException("low-band 'radiance_6' not found");
            }

            // Get the "high" band
            Band hiBand = product.getBand("radiance_10");

            if (hiBand == null)
            {
                throw new IOException("hi-band 'radiance_10' not found");
            }

            // Print out, what we are going to do...
            System.out.println("writing NDVI raw image file "
                    + outputPath
                    + " containing " + w + " x " + h
                    + " pixels of type byte (value range 0-255)...");

            // Create an output stream for the NDVI raw data
            FileImageOutputStream outputStream = new FileImageOutputStream(new File(outputPath));

            // Create a buffer for reading a single scan line of the low-band
            float[] lowBandPixels = new float[w];

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
        }
    }