package com.example.gpi.indexapi.controller;

import com.example.gpi.indexapi.service.GPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class GPIController {

    @Autowired
    GPIService gpiService;

    private static final String EXTENSION=".CSV";
    private static final String APPENDER = "_";

    @GetMapping(value = "/download/{postalCode}", produces = "text/csv")
    public void downloadCSV( HttpServletResponse response, @PathVariable("postalCode") String postalCode)  throws IOException {
       response.setContentType("text/csv");
       SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
       String date = formatter.format(new Date(System.currentTimeMillis()));
       String fileName= new StringBuffer()
                        .append(postalCode)
                        .append(APPENDER)
                        .append(date)
                        .append(EXTENSION).toString();
       response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
       gpiService.downloadData(response.getWriter(),postalCode);
   }
}
