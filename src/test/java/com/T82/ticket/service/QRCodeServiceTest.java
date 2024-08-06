package com.T82.ticket.service;

import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class QRCodeServiceTest {

    @Autowired
    private QRCodeService qrCodeService;

    @Test
    public void testGenerateQRCode() throws WriterException, IOException {
        byte[] qrCodeData = qrCodeService.generateQRCode("1", 200, 200);
        assertNotNull(qrCodeData);
    }
}