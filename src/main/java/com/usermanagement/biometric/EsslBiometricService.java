package com.usermanagement.biometric;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@Service
public class EsslBiometricService {

//    // ✅ Yeh apne eBioServer ke according change karo
//	  // ✅ application.properties se values aayengi
//    @Value("${essl.server.url}")
//    private String esslUrl;
//
//    @Value("${essl.server.username}")
//    private String username;
//
//    @Value("${essl.server.password}")
//    private String password;
//
//    @Value("${essl.server.location:}") // default blank
//    private String location;
//
//    // ─────────────────────────────────────────────
//    // GetDeviceLogs - Punch records fetch karo
//    // ─────────────────────────────────────────────
//    public String getDeviceLogs(String logDate) throws Exception {
//
//        String soapRequest = """
//            <?xml version="1.0" encoding="utf-8"?>
//            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
//                           xmlns:xsd="http://www.w3.org/2001/XMLSchema"
//                           xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
//              <soap:Body>
//                <GetDeviceLogs xmlns="http://tempuri.org/">
//                  <UserName>%s</UserName>
//                  <Password>%s</Password>
//                  <Location>%s</Location>
//                  <LogDate>%s</LogDate>
//                </GetDeviceLogs>
//              </soap:Body>
//            </soap:Envelope>
//            """.formatted(username, password, location, logDate);
//
//        return sendSoapRequest(soapRequest, "GetDeviceLogs");
//    }
//
//    // ─────────────────────────────────────────────
//    // SOAP Request Send karo
//    // ─────────────────────────────────────────────
//    private String sendSoapRequest(String soapBody, String action) throws Exception {
//
//        URL url = new URL(esslUrl);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//        conn.setRequestProperty("SOAPAction", 
//            "\"http://tempuri.org/" + action + "\"");
//        conn.setDoOutput(true);
//        conn.setConnectTimeout(10000);
//        conn.setReadTimeout(15000);
//
//        // Request body bhejo
//        try (OutputStream os = conn.getOutputStream()) {
//            os.write(soapBody.getBytes("utf-8"));
//        }
//
//        // Response lo
//        InputStream is = conn.getResponseCode() == 200 
//            ? conn.getInputStream() 
//            : conn.getErrorStream();
//
//        return new String(is.readAllBytes(), "utf-8");
//    }
//
//    // ─────────────────────────────────────────────
//    // XML Response parse karo - Result nikalo
//    // ─────────────────────────────────────────────
//    public String parseResult(String xmlResponse, String resultTag) throws Exception {
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        factory.setNamespaceAware(true);
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(
//            new ByteArrayInputStream(xmlResponse.getBytes("utf-8"))
//        );
//        NodeList nodes = doc.getElementsByTagNameNS("*", resultTag);
//        if (nodes.getLength() > 0) {
//            return nodes.item(0).getTextContent();
//        }
//        return "";
//    }
}
