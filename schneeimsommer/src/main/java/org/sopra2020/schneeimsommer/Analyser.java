package org.sopra2020.schneeimsommer;


public class Analyser {
    public final static float minSnow= 0f;
    public final static float maxSnow= 0f;

    private float max;
    private float min;
    private int quantitySnow; //quantity of snow pixels
    private float percentSnow; // Percent of snow in the measurement
    private float[][] winterData;
    private float[][] summerData;
    private Boolean[][] snowMask;
    /*

     */
    public Analyser(float[][] winterData, float[][] summerData){
    this.winterData=winterData;
    this.summerData=summerData;
    Boolean[][] snowMask = new Boolean[winterData.length] [winterData[0].length];
        for (int i=0;i<snowMask.length;i++){
            for (int j=0; j<snowMask[0].length; j++){
                if(winterData[i][j]<=maxSnow && winterData[i][j]>=minSnow
                        && summerData[i][j] > maxSnow && summerData[i][j]<minSnow){
                    snowMask[i][j]=true;
                }else snowMask[i][j] = false;
        }
    }

    /**
     *
     * @param 2-dimansional array to search the max value
     * @return returns the max value of the given array
     */
    public float getMax(float[][] data){
        float max=Float.MIN_VALUE;
        for(int i=0;i<data.length;i++){
            for (int j=0; j < data[0].length; j++) {
                if(data[i][j]>max){
                    max=data[i][j];
                }
            }
        }
        return max;
    }

    /**
     *
     * @param data 2-dimansional array to search the min value
     * @return returns the min value  of the given array
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
     * @return
     */
    public int clean(int quantitySummerSnow, int quantityWinterSnow){
        int realSnow= quantityWinterSnow - quantitySummerSnow;
        if(realSnow<0){
            System.err.println("Error: To much snow in Summer");
        }
        return realSnow;
    }

    private Snow[][] colorSnow(){

    private Snow[winterData.length] [winterData[0].length] snow = new Snow[][];

    for (int i=0;i<snow.length;i++){
        for (int j=0; j<snow[0].length; j++){

        }
        }
    }
    }
    private boolean isSnow(float f){
        if(f<=maxSnow && f>=minSnow){
        return true;
        }else return false;
    }

}
