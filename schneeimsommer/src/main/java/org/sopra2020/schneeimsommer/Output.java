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
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static jdk.nashorn.internal.objects.NativeMath.max;

/**
 * Creates the output image which shows the real and the fake snow
 */

public class Output
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
        String inputPath = "D:\\Tatjana\\Documents\\Studium\\SoPra2020\\S1B_IW_GRDH_1SDV_20200302T171606_20200302T171631_020516_026E05_DC99.zip";
        String outputPath = "D:\\Tatjana\\Desktop\\";

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
        float[] Pixels = new float[rechteck.width];//länge
        float allmax = 0;
        float allmin = 10000;
 //########################################################################################################################################
        // Ermittlung des min und max Wertes
        for (int i = 0; i < rechteck.height; i++) {

            raster.getPixels(10550, 7380 + i, rechteck.width, 1, Pixels);

            if (allmin > Floats.min(Pixels)) {
                allmin = Floats.min(Pixels);
            }
            if (allmax < Floats.max(Pixels)) {
                allmax = Floats.max(Pixels);
            }
            System.out.println(Floats.max(Pixels) + " " + Floats.min(Pixels));
        }
//###########################################################################################################################################
        //Erstellung der Daten in 2D Float
        float[][]Pixfl= new float[rechteck.height][rechteck.width];
        for(int i =0;i<rechteck.height;i++) {
            Pixfl[i] = raster.getPixels(10550, 7380+i, rechteck.width, 1, (float[]) null);
        }

//############################################################################################################################################################################


        int test2 = 123;


//Normalisierung




        byte[][] PixByt = new byte[Pixfl.length][Pixfl[0].length];

        int[][] PixInt = new int[Pixfl.length][Pixfl[1].length];
        for(int j=0;j<Pixfl.length;j++) {
            for (int i = 0; i < Pixfl[0].length; i++) {
                PixByt[j][i] = (byte) ((Pixfl[j][i] - allmin) / (allmax - allmin) * 255);
                PixInt[j][i] = (int) ((Pixfl[j][i] - allmin) / (allmax - allmin) * 255);
            }
        }

        System.err.println(allmax + "  " + allmin);
//###########################################################################################################################################################
        //Abspeichern

        try {
            PrintWriterProgressMonitor pm = new PrintWriterProgressMonitor(System.out);

            Band newBand = new Band("output", ProductData.TYPE_INT32, rechteck.width, rechteck.height);
            ProductData.Int newProductData = new ProductData.Int(PixInt.length*PixInt[1].length);
            int[] PixIntFlat= new int[PixInt.length*PixInt[1].length];
            BufferedImage bi = new BufferedImage(rechteck.width,rechteck.height,BufferedImage.TYPE_INT_RGB);
            for(int row=0;row<PixInt.length;row++){
                for(int col=0;col<PixInt[1].length;col++){
                    // PixIntFlat[row*PixInt.length+col]=PixInt[row][col];
                    // System.out.println(row+", "+col);
                    bi.setRGB(col,row,PixInt[row][col]);
                }

            }
            /*//newProductData.setElems(PixIntFlat);
            newBand.setData(newProductData);
            ImageInfo imageInfo = ProductUtils.createImageInfo(new RasterDataNode[]{newBand}, true, pm);
            BufferedImage bi2 = ProductUtils.createRgbImage(new RasterDataNode[]{newBand}, imageInfo, pm);*/




            File OutputFile = new File(outputPath + "test.png");
            int test = 2;
            ImageIO.write(bi, "png", OutputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //#######################################################################################################################################################
    private float avg(Float[] f){
        float sum=0;
        for(float a : f){
            sum+=a;
        }
        return sum/f.length;
    }
}