package com.de.gpi.indexapi.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Forecast {
    private String scale;
    private String epochtime;
    private String eevalue;
    private String ewind;
    private String esolar;
    private String gsi;
    private String timeStamp;
    private String energyprice;
    private String co2_g_standard;
    private String co2_g_oekostrom;
    private  String base;
}
