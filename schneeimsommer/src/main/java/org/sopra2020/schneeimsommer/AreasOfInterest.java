package org.sopra2020.schneeimsommer;

import org.esa.snap.core.datamodel.GeoPos;

/**
 * Saves the Ski - and the Referenceareas with their coordinates (Getter and Setter)
 */

public class AreasOfInterest
{
    private String name;
    private GeoPos geoposSki1;
    private GeoPos geoposSki2;
    private GeoPos geoposRef1;
    private GeoPos geoposRef2;

    public AreasOfInterest (String name, GeoPos geoposSki1, GeoPos geoposSki2, GeoPos geoposRef1, GeoPos geoposRef2)
    {
        this.name = name;
        this.geoposSki1 = geoposSki1;
        this.geoposSki2 = geoposSki2;
        this.geoposRef1 = geoposRef1;
        this.geoposRef2 = geoposRef2;
    }

    public String getName ()
    {
        return name;
    }

    public GeoPos getGeoposSki1 ()
    {
        return geoposSki1;
    }

    public GeoPos getGeoposSki2 ()
    {
        return geoposSki2;
    }

    public GeoPos getGeoposRef1 ()
    {
        return geoposRef1;
    }

    public GeoPos getGeoposRef2 ()
    {
        return geoposRef2;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public void setGeoposSki1 (GeoPos geoposSki1)
    {
        this.geoposSki1 = geoposSki1;
    }

    public void setGeoposSki2 (GeoPos geoposSki2)
    {
        this.geoposSki2 = geoposSki2;
    }

    public void setGeoposRef1 (GeoPos geoposRef1)
    {
        this.geoposRef1 = geoposRef1;
    }

    public void setGeoposRef2 (GeoPos geoposRef2)
    {
        this.geoposRef2 = geoposRef2;
    }
}