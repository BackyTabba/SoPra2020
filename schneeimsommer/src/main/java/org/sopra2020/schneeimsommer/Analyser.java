package org.sopra2020.schneeimsommer;

import java.sql.SQLSyntaxErrorException;

/**
 * The Analyser gets up to four datasets as twodimensional array. It compares the measurements from sommer and winter
 * at two different areas to decide if a white area is winter and if its natural or a by human created product.
 */
public class Analyser {
    public final static float minSnow= 0f;  //smalest value for a snow area
    public final static float maxSnow= 0f;  //biggest value for a snow area

    private float max;  //biggest measurement for a snow area
    private float min;  //smalest measurement for a snow area
    private int quantitySnow; //quantity of snow pixels
    private float percentSnow; // Percent of snow in the measurement
    private float[][] winterData;   //mesurments from winter
    private float[][] summerData;   //mesuremets from summer
    private float[][] winterDataRef;   //mesurments from winter as reference
    private float[][] summerDataRef;   //mesurments from summer as reference
    private Boolean[][] snowMask;   //mask of the area, which decides if its snow or just a white
    private Boolean[][] snowMaskRef;   //mask of the reference area, which decides if its snow or just a white area

    /**
     *
     * @param winterData Data set of the snowy area from winter
     * @param summerData Data set of the snowy area from summer
     * @param winterDataRef Data set of the reference area from winter
     * @param summerDataRef Data set of the reference area from summer
     */
    public Analyser(float[][] winterData, float[][] summerData, float[][] winterDataRef,float[][] summerDataRef) {
        this.winterData = winterData;
        this.summerData = summerData;
        this.winterDataRef = winterDataRef;
        this.summerDataRef = summerDataRef;

        snowMask = new Boolean[winterData.length][winterData[0].length]; //creat a reference if a white pixel is snow or something different
        for (int i = 0; i < snowMask.length; i++) {
            for (int j = 0; j < snowMask[0].length; j++) {
                if (isSnow(winterData[i][j]) == true && isSnow(summerData[i][j]) == false) {
                    snowMask[i][j] = true;
                } else snowMask[i][j] = false;
            }
        }
        snowMaskRef = new Boolean[winterDataRef.length][winterDataRef[0].length]; //creat a reference if a white pixel is snow or something different
        for (int i = 0; i < snowMaskRef.length; i++) {
            for (int j = 0; j < snowMask[0].length; j++) {
                if (isSnow(winterData[i][j]) == true && isSnow(summerData[i][j]) == false) {
                    snowMask[i][j] = true;
                } else snowMask[i][j] = false;
            }

        }
    }

    /**
     *
     * @param data
     * @return the max value of the given array
     */
    public float getMax(float[][] data){
        float max=Float.MIN_VALUE;
        for(int i=0; i < data.length; i++){
            for (int j=0; j < data[0].length; j++) {
                if(data[i][j] > max){
                    max = data[i][j];
                }
            }
        }
        return max;
    }

    /**
     *
     * @param data 2-dimansional array to search the min value
     * @return returns the min value of the given array
     */
    public float minOfArray(float[][] data){
        float min=Float.MAX_VALUE;
        for(int i=0;i<data.length;i++) {
            for (int j=0; j < data[0].length; j++) {
                if(data[i][j]<min){
                    min=data[i][j];
                }
            }
        }
        return min;
    }


    /**
     *
     * @param data - the data to be analysed
     * @return returns the quantity of snow pixels
     */
    public int getQuantitySnow(float[][] data){
        int snow=0;
        for(int i=0;i<data.length;i++) {
            for (int j = 0; j < data[0].length; j++) {
                if(data[i][j]>= minSnow && data[i][j] <=maxSnow){
                    snow++;
                }
            }
        }
        return snow;
    }


    /**
     *
     * @param quantitySummerSnow
     * @param quantityWinterSnow
     * @return the quantity of snow pixels
     */
    public int clean(int quantitySummerSnow, int quantityWinterSnow){
        int realSnow= quantityWinterSnow - quantitySummerSnow;
        if(realSnow<0){
            System.err.println("Error: To much snow in Summer");
        }
        return realSnow;
    }

    /**
     *
     * @return a twodimensional snowarray with special color to show at the final results
     */
    private Snow[][] colorSnow(){

        Snow[][] snow = new Snow[winterData.length] [winterData[0].length];

        for (int i=0;i<snow.length;i++){
            for (int j=0; j<snow[0].length; j++){
                snow[i][j].setWorthOfGrey(winterData[i][j]);
             //   snow[i][j].setRgb( );             RGB Werte müssen noch gesetzt werden.
                //   Entweder über eine Umrechnung aus den Graustufen oder über die Farbe fürs Bild.
                if (isSnow(winterData[i][j])==true) {
                    //Kein Schnee
                    System.err.println("NoSnow at all");
                }else if(clean(getQuantitySnow(winterDataRef),getQuantitySnow(summerDataRef))>=5){ // 5 = mistake acceptance
                    snow[i][j].setRealSnow(true);// real snow
                }else{
                    snow[i][j].setRealSnow(false);// fake snow
                }
            }
        }
        return snow;
    }

    /**
     *
     * @param f
     * @return
     */
    private boolean isSnow(float f){
        if(f<=maxSnow && f>=minSnow){
        return true;
        }else return false;
    }

    }