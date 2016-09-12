package com.ars.service.extradata;

import com.ars.dao.extradata.ExtraDataDAO;
import genarated.routs.Rout;
import genarated.routs.Routs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

/**
 * Created by JJ on 7/19/16.
 */
public class ExtraDataService {
    public static Routs getRouts() {
        String routsXML;
        Routs routs = null;
        ExtraDataDAO extraDataDAO = new ExtraDataDAO();
        routsXML = extraDataDAO.getData("routs");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Routs.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(routsXML);
            routs = (Routs) jaxbUnmarshaller.unmarshal(reader);
        } catch (Exception e) {
            return null;
        }
        return routs;
    }
}
