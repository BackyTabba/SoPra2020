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

// The class for creating the output as a picture

public class Output
{
    /**
     * Writes the data in a new output.png in the overgiven path
     * @param outputPath    The path where the output should be placed
     * @param data  The data to write in the output
     */

    public static void writeData (String outputPath, Snow [][] data)
    {
        try
        {
            BufferedImage bi = new BufferedImage (data.length, data[0].length, BufferedImage.TYPE_INT_RGB);
            for (int row = 0; row < data.length; row++)
            {
                for (int col = 0; col < data[0].length; col++)
                {
                    bi.setRGB (row, col, data[row][col].getRgb());
                }
            }

            File OutputFile = new File (outputPath + "output.png");
            ImageIO.write (bi,"png", OutputFile);

            System.out.println ("Output done!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}