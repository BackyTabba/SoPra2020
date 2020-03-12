package org.sopra2020.schneeimsommer;

import com.bc.ceres.glevel.MultiLevelImage;
import com.google.common.primitives.Floats;
import org.esa.s1tbx.io.imageio.ImageIOReader;
import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.bc.ceres.core.PrintWriterProgressMonitor;
import org.esa.snap.core.util.ProductUtils;
import org.esa.snap.dataio.envisat.DataTypes;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static jdk.nashorn.internal.objects.NativeMath.max;

public class App
{
    /*public static void main (String[] args)
    {
        String inputPath = "",outputPath="";
        if (args.length < 2)
        {
            System.out.println("parameter usage: <input-file> <output-file>");
            return;
        }

        // Get arguments
       // String inputPath = args[0];
        //String outputPath = args[1];
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

        Product product = null;
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
         /*  if (args.length < 2)
           {
                System.out.println("parameter usage: <input-file> <output-file>");
                return;
            }*/

            // Get arguments
            String inputPath = "C:\\Users\\Lennart\\Downloads\\S1B_IW_GRDH_1SDV_20200302T171606_20200302T171631_020516_026E05_DC99.zip";
            String outputPath = "C:\\temp\\";

            try
            {
                // Pass arguments to actual program code
                writeData(inputPath, outputPath);
                DataManager dm= new DataManager(inputPath);
                float[][] asd = dm.extractData(dm.getProduct(),new Rectangle(10550, 7380, 1000, 600));
                int b = 1;
            }
            catch (Exception e)
            {
                System.out.println("error: " + e.getMessage());
            }
        }

    public static void writeData(String inputPath,String outputPath)throws IOException {




        Product product = ProductIO.readProduct(inputPath);
        Band band = product.getBand("Amplitude_VH");
        MultiLevelImage image = band.getGeophysicalImage();
        Rectangle rechteck = new Rectangle(10550, 7380, 1000, 600);
        Raster raster = image.getData(rechteck);
        //FileImageOutputStream outputStream = new FileImageOutputStream(new File("."));
        float[] Pixels = new float[rechteck.width];//länge

        //For each pixelreihe

        // float allavg=Pixels[];
        float allmax = 0;
        float allmin = 10000;
        for (int i = 0; i < rechteck.height; i++) {

            raster.getPixels(10550, 7380 + i, rechteck.width, 1, Pixels);
            //  outputStream.writeByte(Pixels[i]);
            //outputStream.writeFloat(Pixels[i]);


            //outputStream.write(122);
            //System.out.println(Arrays.toString(Pixels));#

            if (allmin > Floats.min(Pixels)) {
                allmin = Floats.min(Pixels);
            }
            if (allmax < Floats.max(Pixels)) {
                allmax = Floats.max(Pixels);
            }
            System.out.println(Floats.max(Pixels) + " " + Floats.min(Pixels));

            // Arrays.stream(Pixels).average().orElse(Double.NaN);

        }

        float[][]Pixfl= new float[rechteck.height][rechteck.width];
        for(int i =0;i<rechteck.height;i++) {
            Pixfl[i] = raster.getPixels(10550, 7380+i, rechteck.width, 1, (float[]) null);
        }

        //############################################################################################################################################################################


        int test2 = 123;







        byte[][] PixByt = new byte[Pixfl.length][Pixfl[0].length];

        int[][] PixInt = new int[Pixfl.length][Pixfl[1].length];
        for(int j=0;j<Pixfl.length;j++) {
            for (int i = 0; i < Pixfl[0].length; i++) {
                PixByt[j][i] = (byte) ((Pixfl[j][i] - allmin) / (allmax - allmin) * 255);
                PixInt[j][i] = (int) ((Pixfl[j][i] - allmin) / (allmax - allmin) * 255);
            }
        }

        System.err.println(allmax + "  " + allmin);



/*            byte[] PixInt=new byte[Pixels.length];
            for( int j=0; i<Pixels.length;j++){

                PixInt[j] =(byte) Pixels[j];
            }*/

        //Stream intstream = new Stream<Integer>();

        // IntStream stream = Arrays.stream(PixInt);


        try {

           // product.getRasterDataNodes();
            PrintWriterProgressMonitor pm = new PrintWriterProgressMonitor(System.out);
           // RasterDataNode rdn = product.getRasterDataNode("Amplitude_VH");
            //(RasterDataNode[])product.getRasterDataNodes().toArray()

            Band newBand = new Band("output", ProductData.TYPE_INT32, rechteck.width, rechteck.height);
            // newBand.setDataElems(raster.getget)
            ProductData.Int newProductData = new ProductData.Int(PixInt.length*PixInt[1].length);//?
            int[] PixIntFlat= new int[PixInt.length*PixInt[1].length];
            for(int row=0;row<PixInt.length;row++){
                for(int col=0;col<PixInt[1].length;col++){
                    PixIntFlat[row*PixInt.length+col]=PixInt[row][col];
                }

            }
            newProductData.setElems(PixIntFlat);

            newBand.setData(newProductData);


            ImageInfo imageInfo = ProductUtils.createImageInfo(new RasterDataNode[]{newBand}, true, pm);
            BufferedImage bi2 = ProductUtils.createRgbImage(new RasterDataNode[]{newBand}, imageInfo, pm);


            //         BufferedImage bi = new BufferedImage(raster.getWidth(),raster.getHeight(),BufferedImage.TYPE_INT_RGB);
            // bi.setData(raster);

            //               ByteArrayInputStream BAISPixByt = new ByteArrayInputStream(PixByt);
//                BufferedImage bufferedImage = ImageIO.read(BAISPixByt);


            File OutputFile = new File(outputPath + "out.png");


            int test = 2;
            ImageIO.write(bi2, "png", OutputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

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