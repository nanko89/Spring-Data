package com.example.springdata.service.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

@Component
public class XmlConverterImpl implements XmlConverter {
    @Override
    public <T> T deserialize(String input, Class<T> type) {
        try {
            var ctx = JAXBContext.newInstance(type);
            var unmarshaller = ctx.createUnmarshaller();

            var sr = new StringReader(input);

            return (T) unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String serialize(Object o) {
        return o.toString();
    }
}
