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
     * @param product
     * @param rectangle
     * @return filtered product data by the rectangleframe
     */
    public float[][] extractData(Product product, Rectangle rectangle){
        Band band = product.getBand("Amplitude_VH"); //Anpassen für Funktionserweiterungen
        // MultiLevelImage image = band.getGeophysicalImage();
        // Raster raster = band.getData(rectangle);

        float[][]pixfl= new float[rectangle.height][rectangle.width];

        for(int i =0;i<rectangle.height;i++) {
            try {
                pixfl[i] = band.readPixels(rectangle.x, rectangle.y+rectangle.height-i, rectangle.width, 1, (float[]) null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return pixfl;
    }






}
