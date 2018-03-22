package com.ElegantDevelopment.iacWebshop.util;

import com.soap.ws.unique.Address;
import soapserver.Unique;
import soapserver.UniqueService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

// internal import
import soapserver.Unique;
import soapserver.UniqueService;
import com.soap.ws.unique.Address;

public class UniqueCode {

    //    uniqueCode function connects to soapServer, returns uniqueCode
    public void uniqueCode(String name, Address adress, double amount) throws MalformedURLException {
//        LOG.info("service ... wait 30 seconds");

//        Connect to server
        QName qName = new QName("http://soapServer/",
                "UniqueService");
        URL url = new URL("http://localhost:9999/webshop-soap/unique");
        Service service = UniqueService.create(url, qName);
        Unique port = (Unique) service.getPort(Unique.class);

//        return unique Code
        int returnMsg = port.uniqueCode(name, adress, amount);
//        LOG.info("return: " + returnMsg);
    }


}
