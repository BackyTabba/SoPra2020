package org.sopra2020.schneeimsommer;

/**
 * The Analyser gets up to four datasets as twodimensional array. It compares the measurements from summer and winter
 * at two different areas to decide if a white area is winter and if its natural or a by human created product.
 */

public class Analyser
{
    public final static float minSnow = 0f;  //smallest value for a snow area
    public final static float maxSnow = 33f;  //biggest value for a snow area

    private float max;  //biggest measurement for a snow area
    private float min;  //smallest measurement for a snow area
    private int quantitySnow;   //quantity of snow pixels
    private float percentSnow;  //percent of snow in the measurement
    private float percentSnowRef;
    private float quantitySnowRef;
    private int quantityFalseSnow;
    private float percentFalseSnow;
    private int il,jl,irl,jrl;
    private float[][] winterData;   //measurements from winter
    private float[][] summerData;   //measurements from summer
    private float[][] winterDataRef;   //measurements from winter as reference
    private float[][] summerDataRef;   //measurements from summer as reference
    private Boolean[][] snowMask;   //mask of the area which decides if its snow or just a white area
    private Boolean[][] snowMaskRef;    //mask of the reference area which decides if its snow or just a white area
    private Snow[][] snow;  //final array for the output


    /**
     * @param winterData    Data set of the snowy area from winter
     * @param summerData    Data set of the snowy area from summer
     * @param winterDataRef Data set of the reference area from winter
     * @param summerDataRef Data set of the reference area from summer
     */

    public Analyser (float[][] winterData, float[][] summerData, float[][] winterDataRef, float[][] summerDataRef)
    {
        this.winterData = winterData;
        this.summerData = summerData;
        this.winterDataRef = winterDataRef;
        this.summerDataRef = summerDataRef;

        if(winterData[0].length>summerData[0].length){
            jl=summerData[0].length;
        }else{
            jl=winterData[0].length;
        }
        if(winterData.length>summerData.length){
            il=summerData.length;
        }else{
            il=winterData.length;
        }

        if(winterDataRef[0].length>summerDataRef[0].length){
            jrl=summerDataRef[0].length;
        }else{
            jrl=winterDataRef[0].length;
        }
        if(winterDataRef.length>summerDataRef.length){
            irl=summerDataRef.length;
        }else{
            irl=winterDataRef.length;
        }
        int countSnow=0,count=0,countRef=0,countSnowRef=0;
        snowMask = new Boolean [il] [jl];  //creates a reference if a white pixel is snow or something different
        for (int i =0; i< il;i++){//data[j][i]
            for (int j = 0; j<jl;j++){
                snowMask[i][j]=false;
                if(isSnow(winterData[i][j])){snowMask[i][j]=true;}
                if(isSnow(summerData[i][j])){snowMask[i][j]=false;}
                if(snowMask[i][j]){countSnow++;}
                count++;
            }
        }
        snowMaskRef = new Boolean [irl] [jrl];  //creates a reference if a white pixel is snow or something different
        for (int i =0; i< irl;i++) {//data[j][i]
            for (int j = 0; j < jrl; j++) {
                snowMaskRef[i][j]=false;
                if(isSnow(winterDataRef[i][j])){snowMaskRef[i][j]=true;}
                if(isSnow(summerDataRef[i][j])){snowMaskRef[i][j]=false;}
                if(snowMaskRef[i][j]){countSnowRef++;}
                countRef++;
            }
        }



        percentSnow=countSnow/count;  //percent of snow in the measurement
        percentSnowRef=countSnowRef/countRef;
        quantitySnow=countSnow;   //quantity of snow pixels
        quantitySnowRef=countSnowRef;
        if(percentSnow>percentSnowRef){
            quantityFalseSnow =(int)((percentSnow-percentSnowRef)*quantitySnow);
            percentFalseSnow= quantityFalseSnow /quantitySnow;
        }else{
            quantityFalseSnow =0;
            percentFalseSnow=0;
        }





        
     /*   for (int i = 0; i < il; i++)
        {
            for (int j = 0; j < jl; j++)
            {
                if (isSnow (winterData[i][j]) == true&& isSnow (summerData[i][j]) == false)
                {
                    snowMask[i][j] = true;
                }
                else
                {
                    snowMask[i][j] = false;
                }
            }
        }

        snowMaskRef = new Boolean [il] [jl]; //creates a reference if a white pixel is snow or something different
        for (int i = 0; i < il; i++)
        {
            for (int j = 0; j < jl; j++)
            {
                if (isSnow (winterDataRef[i][j]) == true && isSnow (summerDataRef[i][j]) == false)
                {
                    snowMaskRef [i][j] = true;
                }
                else
                {
                    snowMaskRef [i][j] = false;
                }
            }

        }*/
    }


    /**
     * @param data   2-dimensional array to search the min value
     * @return float    the max value of the given array
     * @see DataManager
     */

    public float getMax (float[][] data)
    {
        float max = Float.MIN_VALUE;
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[0].length; j++)
            {
                if (data[i][j] > max)
                {
                    max = data[i][j];
                }
            }
        }
        return max;
    }


    /**
     * @param data  2-dimensional array to search the min value
     * @return float    returns the min value of the given array
     * @see DataManager
     */

    public float getMin (float[][] data)
    {
        float min = Float.MAX_VALUE;
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[0].length; j++)
            {
                if (data[i][j] < min)
                {
                    min = data[i][j];
                }
            }
        }
        return min;
    }


    /**
     * @param data  the data to be analysed
     * @return int  returns the quantity of snow pixels
     * @see DataManager
     */

    public int getQuantitySnow (float[][] data)
    {
        int snow = 0;
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[0].length; j++)
            {
                if (data[i][j] >= minSnow && data[i][j] <= maxSnow)
                {
                    snow++;
                }
            }
        }
        return snow;
    }


    /**
     * @param quantitySummerSnow    quantity of snow in summer
     * @param quantityWinterSnow    quantity of snow in winter
     * @return int  the quantity of snow pixels
     */

    public int clean (int quantitySummerSnow, int quantityWinterSnow)
    {
        int realSnow = quantityWinterSnow - quantitySummerSnow;
        if (realSnow < 0)
        {
            System.err.println ("Error: Too much snow in summer");
        }
        return realSnow;
    }


    /**
     * Makes the decision if a pixel is snow at all and if it is real or fake snow
     * @return Snow[][]    snowarray with special color to show at the final results
     */

    public Snow[][] colorSnow()
    {
        snow = new Snow [il] [jl];
        for (int i = 0; i < snow.length; i++)
        {
            for (int j = 0; j < snow[0].length; j++)
            {
                snow[i][j]=new Snow();
                snow[i][j].setIsSnowAtAll (snowMask[i][j]);
                snow[i][j].setWorthOfGrey (winterData[i][j]);
                //snow[i][j].setRgb( );             RGB Werte müssen noch gesetzt werden.
                //Entweder über eine Umrechnung aus den Graustufen oder über die Farbe fürs Bild.

                if (snow[i][j].getIsSnowAtAll() == false)
                {
                   //System.err.println("No snow at all");   //No snow
                }
                else if (clean (getQuantitySnow(winterDataRef), getQuantitySnow(summerDataRef)) >= 5)   // 5 mistakes are accepted
                {
                    snow[i][j].setRealSnow (true);   // real snow
                }
                else
                {
                    snow[i][j].setRealSnow (false);  // fake snow
                }
            }
        }
        return snow;
    }

    /**
     * @param f an example measurement value
     * @return boolean  returns true if f is between maxSnow and minSnow
     */

    private boolean isSnow (float f)
    {
        if (f <= maxSnow && f >= minSnow)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}