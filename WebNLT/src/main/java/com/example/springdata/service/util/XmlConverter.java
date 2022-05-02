package com.example.springdata.service.util;

public interface XmlConverter {

     <T> T deserialize(String input, Class<T> tClass);

     String serialize(Object o);
}
