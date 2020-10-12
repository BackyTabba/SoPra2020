package org.sopra2020.schneeimsommer;

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

    public int[] getRgbAsArray()
    {
        return rgb;
    }
    public int getRgb(){
        int SingleRgb =rgb[0];
        SingleRgb=SingleRgb<<8;
        SingleRgb+=rgb[1];
        SingleRgb=SingleRgb<<8;
        SingleRgb+=rgb[2];
        return SingleRgb;
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