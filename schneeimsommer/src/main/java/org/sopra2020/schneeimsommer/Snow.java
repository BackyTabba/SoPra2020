package org.sopra2020.schneeimsommer;

public class Snow
{
    private float worthOfGrey;
    private int [] rgb = new int [3];

    public float getWorthOfGrey ()
    {
        return worthOfGrey;
    }

    public int[] getRgb ()
    {
        return rgb;
    }

    public void setWorthOfGrey (float worthOfGrey)
    {
        this.worthOfGrey = worthOfGrey;
    }

    public void setRgb (int [] rgb)
    {
        this.rgb = rgb;
    }
}