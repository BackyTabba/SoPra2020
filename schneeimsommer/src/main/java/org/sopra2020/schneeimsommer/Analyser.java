package org.sopra2020.schneeimsommer;

// The Analyser gets up to four datasets as twodimensional array. It compares the measurements from summer and winter
// at two different areas to decide if a white area is winter and if its natural or a by human created product.

public class Analyser
{
    public final static float minSnow = 0f;  // smallest value for a snow area
    public final static float maxSnow = 33f;  // biggest value for a snow area

    private float max;
    private float min;
    private float quantitySnow;
    private float percentSnow;
    private float percentSnowRef;
    private float quantitySnowRef;
    private float quantityFalseSnow;
    private float percentFalseSnow;
    private int il, jl, irl, jrl;
    private float[][] winterData;
    private float[][] summerData;
    private float[][] winterDataRef;
    private float[][] summerDataRef;
    private Boolean[][] snowMask;
    private Boolean[][] snowMaskRef;
    private Snow[][] snow;

    public float getPercentSnow ()
    {
        return percentSnow;
    }

    public float getPercentSnowRef ()
    {
        return percentSnowRef;
    }

    public float getQuantityFalseSnow ()
    {
        return quantityFalseSnow;
    }

    public float getPercentFalseSnow ()
    {
        return percentFalseSnow;
    }


    /**
     * A function that analyses the snow pixels
     * @param winterData    Data set of the snowy area from winter
     * @param summerData    Data set of the snowy area from summer
     * @param winterDataRef Data set of the reference area from winter
     * @param summerDataRef Data set of the reference area from summer
     */

    public Analyser (float [][] winterData, float [][] summerData, float [][] winterDataRef, float [][] summerDataRef)
    {
        this.winterData = winterData;
        this.summerData = summerData;
        this.winterDataRef = winterDataRef;
        this.summerDataRef = summerDataRef;

        if (winterData[0].length > summerData[0].length)
        {
            jl = summerData[0].length;
        }
        else
        {
            jl = winterData[0].length;
        }
        if (winterData.length > summerData.length)
        {
            il = summerData.length;
        }
        else
        {
            il = winterData.length;
        }
        if (winterDataRef[0].length > summerDataRef[0].length)
        {
            jrl = summerDataRef[0].length;
        }
        else
        {
            jrl = winterDataRef[0].length;
        }
        if (winterDataRef.length > summerDataRef.length)
        {
            irl = summerDataRef.length;
        }
        else
        {
            irl = winterDataRef.length;
        }
        float countSnow = 0, count = 0, countRef = 0, countSnowRef = 0;

        // Creates a reference if a white pixel is snow or something different
        snowMask = new Boolean [il][jl];
        for (int i = 0; i < il; i++)
        {
            for (int j = 0; j < jl; j++)
            {
                snowMask [i][j] = false;
                if (isSnow (winterData [i][j]))
                {
                    snowMask [i][j] = true;
                }
                if (isSnow (summerData [i][j]))
                {
                    snowMask [i][j] = false;
                }
                if (snowMask [i][j])
                {
                    countSnow++;
                }
                count++;
            }
        }

        // Creates a reference if a white pixel is snow or something different
        snowMaskRef = new Boolean [irl][jrl];
        for (int i = 0; i < irl; i++)
        {
            for (int j = 0; j < jrl; j++)
            {
                snowMaskRef [i][j] = false;
                if (isSnow (winterDataRef [i][j]))
                {
                    snowMaskRef [i][j] = true;
                }
                if (isSnow (summerDataRef [i][j]))
                {
                    snowMaskRef [i][j] = false;
                }
                if (snowMaskRef [i][j])
                {
                    countSnowRef++;
                }
                countRef++;
            }
        }

        // Percent of snow in the measurement
        percentSnow = (countSnow / count) * 100;
        percentSnowRef = (countSnowRef / countRef) * 100;

        // Quantity of snow pixels
        quantitySnow = countSnow;
        quantitySnowRef = countSnowRef;

        if (percentSnow > percentSnowRef)
        {
            quantityFalseSnow = (int) ((percentSnow - percentSnowRef) * quantitySnow);
            percentFalseSnow = quantityFalseSnow / quantitySnow;
        }
        else
        {
            quantityFalseSnow = 0;
            percentFalseSnow = 0;
        }
    }


    /**
     * A function that calculates a maximum of the data
     * @param data   2-dimensional array to search the min value
     * @return float    the max value of the given array
     * @see DataManager
     */

    public float getMax (float [][] data)
    {
        float max = Float.MIN_VALUE;
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[0].length; j++)
            {
                if (data [i][j] > max)
                {
                    max = data [i][j];
                }
            }
        }
        return max;
    }


    /**
     * A function that calculates the minimum of the data
     * @param data  2-dimensional array to search the min value
     * @return float    returns the min value of the given array
     * @see DataManager
     */

    public float getMin (float [][] data)
    {
        float min = Float.MAX_VALUE;
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[0].length; j++)
            {
                if (data [i][j] < min)
                {
                    min = data [i][j];
                }
            }
        }
        return min;
    }


    /**
     * A function that calculates the quantity of snow
     * @param data  the data to be analysed
     * @return int  returns the quantity of snow pixels
     * @see DataManager
     */

    public int getQuantitySnow (float [][] data)
    {
        int snow = 0;
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[0].length; j++)
            {
                if (data [i][j] >= minSnow && data [i][j] <= maxSnow)
                {
                    snow++;
                }
            }
        }
        return snow;
    }


    /**
     * A function that verifies if the quantity of snow in summer and winter data makes sense
     * @param quantitySummerSnow    quantity of snow in summer
     * @param quantityWinterSnow    quantity of snow in winter
     * @return int  the quantity of snow pixels
     */

    public boolean validateData (int quantityWinterSnow, int quantitySummerSnow)
    {
        int realSnow = quantityWinterSnow - quantitySummerSnow;
        boolean validation = true;
        if (realSnow < (-5))
        {
            validation = false;
            System.err.println ("Error: Too much snow in summer");
        }
        return validation;
    }


    /**
     * Makes the decision if a pixel is snow at all
     * @return Snow[][]    snowarray with special color to show at the final results
     */

    public Snow [][] colorSnow ()
    {
        snow = new Snow[il][jl];
        boolean winterHasMoreSnow = validateData(getQuantitySnow(winterDataRef), getQuantitySnow(summerDataRef));
        for (int i = 0; i < snow.length; i++)
        {
            for (int j = 0; j < snow[0].length; j++)
            {
                snow [i][j] = new Snow ();
                snow[i][j].setIsSnowAtAll (snowMask [i][j]);
                snow[i][j].setWorthOfGrey (winterData [i][j]);

                if (snow[i][j].getIsSnowAtAll())
                {
                    if (winterHasMoreSnow == true)
                    {
                        snow[i][j].setRealSnow(true);
                    }
                    else if (winterHasMoreSnow == false)
                    {
                        snow[i][j].setRealSnow(false);
                    }
                }
            }
        }
        return snow;
    }


    /**
     * A function that decides if a pixel is snow or not
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