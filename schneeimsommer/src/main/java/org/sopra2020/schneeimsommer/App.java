package org.sopra2020.schneeimsommer;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;

import java.io.IOException;
public class App
{
    public static void main (String[] args)
    {
        Product product = null;
        try
        {
            product = ProductIO.readProduct("test");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
