package com.example.gpi.indexapi.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class GreenPowerIndex {
    private List<Forecast> forecast;
    private Location location;
}
