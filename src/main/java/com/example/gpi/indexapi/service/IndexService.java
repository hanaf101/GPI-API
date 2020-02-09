package com.example.gpi.indexapi.service;

import java.io.PrintWriter;

/*
* Interface for defining connectivity with target systems.
* */
public interface IndexService {
    void downloadData(PrintWriter printWriter, String postalCode);
}
