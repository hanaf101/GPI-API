package com.example.gpi.indexapi.service;

import com.example.gpi.indexapi.model.Forecast;
import com.example.gpi.indexapi.model.GreenPowerIndex;
import com.example.gpi.indexapi.model.Location;
import com.example.gpi.indexapi.utils.GenericUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 *Implement IndexService Interface for connectivity with Green Power Index
 */

@Service
@Slf4j
public class GPIService implements IndexService {


    @Value("${GPI.endpoint}")
    private String gpiEndpoint;

    @Autowired
    private GenericUtils genericUtils;


    @Override
    public void downloadData(PrintWriter printWriter, String pin) throws IOException {
        String NEW_LINE_SEPARATOR = "\n";
        final GreenPowerIndex gpi = genericUtils.readStreamAndGetObject(gpiEndpoint + pin, GreenPowerIndex.class);
        if(gpi != null) {
            try(CSVPrinter csvPrinter = new CSVPrinter(new BufferedWriter(printWriter),
                    CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR))) {
                //Create CSV file header
                csvPrinter.printRecord(getFileHeader());
                for (Forecast forecast : gpi.getForecast()) {
                    csvPrinter.printRecord(getRecord(gpi.getLocation(), forecast));
                }
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

    private List<Object> getRecord(Location location, Forecast forecast) {
        final Format format = new SimpleDateFormat("YYYY-MM-DD HH:mm");
        Date date = new Date(forecast.getTimeStamp());
        String recordTime = format.format(date);
        List<Object> record = new ArrayList<>();
        record.add(location.getZip());
        record.add(location.getCity());
        record.add(recordTime);
        record.add(forecast.getScale());
        record.add(forecast.getEevalue());
        record.add(forecast.getEwind());
        record.add(forecast.getEsolar());
        record.add(forecast.getBase());
        record.add(forecast.getGsi());
        record.add(recordTime);
        record.add(forecast.getEnergyprice());
        record.add(forecast.getCo2_g_standard());
        record.add(forecast.getCo2_g_oekostrom());

        return record;
    }

    private Object[] getFileHeader() {
        final Object[] FILE_HEADER = {
                "Pin Code",
                "City",
                "Time",
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
        return FILE_HEADER;
    }
}
