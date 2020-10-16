package org.sopra2020.schneeimsommer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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

            File OutputFile = new File (outputPath);
            ImageIO.write (bi,"png", OutputFile);

            System.out.println ("Output done!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}