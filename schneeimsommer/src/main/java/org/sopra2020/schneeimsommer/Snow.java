package org.sopra2020.schneeimsommer;

public class Snow
{
    private float worthOfGrey;
    private boolean isSnow;
    private int [] rgb = new int [3];

    public float getWorthOfGrey ()
    {
        return worthOfGrey;
    }

    public boolean getIsSnow ()
    {
        return isSnow;
    }

    public int[] getRgb ()
    {
        return rgb;
    }

    public void setWorthOfGrey (float worthOfGrey)
    {
        this.worthOfGrey = worthOfGrey;
    }

    public void setSnow (boolean isSnow)
    {
        this.isSnow = isSnow;
    }

    public void setRgb (int [] rgb)
    {
        this.rgb = rgb;
    }
}