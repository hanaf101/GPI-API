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

    @RequestMapping(value = "/download/{postalCode}", method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public void downloadCSV( HttpServletResponse response, @PathVariable("postalCode") String postalCode)  throws IOException {
       response.setContentType("text/csv");
       SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
       String date = formatter.format(new Date(System.currentTimeMillis()));
       String fileName= new StringBuffer()
                        .append(postalCode)
                        .append("_")
                        .append(date)
                        .append(".CSV").toString();
       response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
       gpiService.downloadData(response.getWriter(),postalCode);
   }
}
