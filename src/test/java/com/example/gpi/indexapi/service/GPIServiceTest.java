package com.example.gpi.indexapi.service;

import com.example.gpi.indexapi.model.GreenPowerIndex;
import com.example.gpi.indexapi.test.utils.TestUtil;
import com.example.gpi.indexapi.utils.GenericUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import javax.ws.rs.NotFoundException;
import java.io.*;

@RunWith(MockitoJUnitRunner.class)
public class GPIServiceTest {

    @InjectMocks
    GPIService gpiService ;

    @Mock
    GenericUtils genericUtils;

    private GreenPowerIndex greenPowerIndex;

    @Before
    public void init() throws Exception {
        greenPowerIndex = TestUtil.readStreamAndGetObject("GPIResponse", GreenPowerIndex.class);
    }

    @Test
    public void downloadCSVTest() throws Exception {
        Mockito.when(genericUtils.readStreamAndGetObject(Mockito.anyString(), Mockito.any())).thenReturn(greenPowerIndex);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter( new PrintWriter(stringWriter));
        gpiService.downloadData(printWriter, "ABC");
        Assert.assertNotNull(printWriter);
        Assert.assertTrue(printWriter.toString().length()>0);
    }

    @Test(expected = NotFoundException.class)
    public void downloadCSVInvalidPinFile() throws IOException {
        Mockito.when(genericUtils.readStreamAndGetObject(Mockito.anyString(), Mockito.any())).thenReturn(null);
        PrintWriter printWriter = new PrintWriter(System.out);
        gpiService.downloadData(printWriter, "ABC");
    }
}
