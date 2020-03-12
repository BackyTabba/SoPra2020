package org.sopra2020.schneeimsommer;

public class Snow
{
    private float worthOfGrey;
    private boolean isRealSnow;
    private int [] rgb = new int [3];

    public float getWorthOfGrey ()
    {
        return worthOfGrey;
    }

    public boolean getIsSnow ()
    {
        return isRealSnow;
    }

    public int[] getRgb ()
    {
        return rgb;
    }

    public void setWorthOfGrey (float worthOfGrey)
    {
        this.worthOfGrey = worthOfGrey;
    }

    public void setRealSnow (boolean isSnow)
    {
        this.isRealSnow = isSnow;
    }

    public void setRgb (int [] rgb)
    {
        this.rgb = rgb;
    }
}