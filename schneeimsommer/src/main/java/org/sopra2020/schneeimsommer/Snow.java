package org.sopra2020.schneeimsommer;

// This class creates Snow, its characteristics and the colorization for the output
public class Snow
{
    private float worthOfGrey;
    private boolean isRealSnow;
    private boolean isSnowAtAll;
    private int [] rgb = new int [3];

    public float getWorthOfGrey ()
    {
        return worthOfGrey;
    }

    public boolean getIsRealSnow ()
    {
        return isRealSnow;
    }

    public boolean getIsSnowAtAll ()
    {
        return isSnowAtAll;
    }

    public int [] getRgbAsArray ()
    {
        return rgb;
    }


    /**
     * Colors the pixel in different colors
     * @return  The rgb - color as an integer
     */

    public int getRgb ()
    {
        if (worthOfGrey != 0)
        {
            rgb [0] = (int) worthOfGrey;
            rgb [1] = (int) worthOfGrey;
            rgb [2] = (int) worthOfGrey;
        }
        if (isSnowAtAll == true)
        {
            rgb [0] = 0;
            rgb [1] = 0;
            rgb [2] = 255;
        }
        if (isRealSnow == true)
        {
            rgb [0] = 0;
            rgb [1] = 255;
            rgb [2] = 255;
        }

        int singleRgb = rgb [0];
        singleRgb = singleRgb << 8;
        singleRgb += rgb [1];
        singleRgb = singleRgb << 8;
        singleRgb += rgb [2];
        return singleRgb;
    }

    public void setWorthOfGrey (float worthOfGrey)
    {
        this.worthOfGrey = worthOfGrey;
    }

    public void setRealSnow (boolean isRealSnow)
    {
        this.isRealSnow = isRealSnow;
    }

    public void setIsSnowAtAll (boolean isSnowAtAll)
    {
        this.isSnowAtAll = isSnowAtAll;
    }

    public void setRgb (int [] rgb)
    {
        this.rgb = rgb;
    }
}