package com.example.gpi.indexapi.service;

import com.example.gpi.indexapi.model.GreenPowerIndex;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.NotFoundException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j

/*
*Implement IndexService Interface for connectivity with Green Power Index
 */
public class GPIService implements IndexService {


    @Value("${GPI.endpoint}")
    private String gpiEndpoint;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void downloadData(PrintWriter printWriter, String pin) {
        String NEW_LINE_SEPARATOR = "\n";
        //CSV file header
        final Format format = new SimpleDateFormat("YYYY-MM-DD HH:mm");

        final GreenPowerIndex gpi = restTemplate.getForObject(gpiEndpoint + pin, GreenPowerIndex.class);
        if(gpi != null) {
            final Object[] FILE_HEADER = {
                    "Pin Code",
                    "City",
                    "time",
                    "Scale",
                    "EE Value",
                    "E Wind",
                    "E Solar",
                    "Base",
                    "GSI",
                    "Time Stamp",
                    "Energy Price",
                    "CO2 Standard",
                    "CO2 Oekostrom"
            };
            try(CSVPrinter csvPrinter = new CSVPrinter(new BufferedWriter(printWriter),
                    CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR))) {
                //Create CSV file header
                csvPrinter.printRecord(FILE_HEADER);

                gpi.getForecast().forEach(forecast -> {
                    Date date = new Date(Long.parseLong(forecast.getTimeStamp()));
                    String recordTime = format.format(date);
                    try {
                        csvPrinter.printRecord(gpi.getLocation().getZip(), gpi.getLocation().getCity(),
                                recordTime, forecast.getScale(), forecast.getEevalue(), forecast.getEwind(),
                                forecast.getEsolar(), forecast.getBase(), forecast.getGsi(), recordTime,
                                forecast.getEnergyprice(), forecast.getCo2_g_standard(), forecast.getCo2_g_oekostrom()
                        );
                    } catch (IOException  e) {

                    }
                });
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                printWriter.flush();
                printWriter.close();
            }
        } else {
            throw new NotFoundException();
        }
    }
}
