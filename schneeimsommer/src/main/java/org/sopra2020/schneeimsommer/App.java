package org.sopra2020.schneeimsommer;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Product product = null;
        try {
            product = ProductIO.readProduct("test");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println( "Hello World!" );

    }
}
