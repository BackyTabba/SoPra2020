package org.sopra2020.schneeimsommer;

import com.bc.ceres.glevel.MultiLevelImage;
import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.Product;

import java.awt.*;
import java.awt.image.Raster;
import java.io.IOException;

public class DataManager {
    public DataManager(String inputPath){
        this.inputPath=inputPath;
    }

    private String inputPath;
    private Product product;

    /**
     * If not initialized, it runs loadProduct(inputPath)
     * @return product
     */
    public Product getProduct(){
        if(this.product==null){
            loadProduct();
        }
        return this.product;
    }

    /**
     *  Loads product out of inputPath
     */
    private void loadProduct()throws NullPointerException{
        try {
            this.product = ProductIO.readProduct(inputPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     *
     * @param rectangle
     * @return filtered product data by the rectangleframe
     */
    public float[][] extractData(Rectangle rectangle) throws IOException {
        Band band = product.getBand("Amplitude_VH"); //Anpassen für Funktionserweiterungen
        // MultiLevelImage image = band.getGeophysicalImage();
        // Raster raster = band.getData(rectangle);

        float[][]pixfl= new float[rectangle.height][rectangle.width];

        System.out.println("Raster Height:"+band.getRasterHeight()+" RasterWidth:"+band.getRasterWidth());
        for(int i =0;i<rectangle.height;i++) {
            try {
                System.out.println(
                        "x:" + rectangle.x
                        +" y:" + (rectangle.y+rectangle.height-i)
                        +" width:"+rectangle.width
                        +" height:"+ 1
                );
                // Sollten wir hier band.getPixels() nutzen wie in Output.java?
                pixfl[i] = band.readPixels(rectangle.x, rectangle.y+rectangle.height-i, rectangle.width, 1, (float[]) null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Produkt schließen (graceful) damit es woanders neu geöffnet werden kann
            product.closeIO();
            product.dispose();
        }

        return pixfl;
    }






}
