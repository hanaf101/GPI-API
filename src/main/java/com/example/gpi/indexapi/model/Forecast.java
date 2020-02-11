package com.example.gpi.indexapi.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Forecast {
    private double scale;
    private long epochtime;
    private int eevalue;
    private int ewind;
    private int esolar;
    private int gsi;
    private long timeStamp;
    private double energyprice;
    private int co2_g_standard;
    private int co2_g_oekostrom;
    private  int base;
}
