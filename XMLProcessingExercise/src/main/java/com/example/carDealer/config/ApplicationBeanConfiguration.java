package com.example.carDealer.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
//        ModelMapper modelMapper = new ModelMapper();
//
//        Converter<String, LocalDateTime> toStringDate = new AbstractConverter<>() {
//            @Override
//            protected LocalDateTime convert(String source) {
//                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss");
//                return LocalDateTime.parse(source, format);
//            }
//        };
//
//        modelMapper.createTypeMap(String.class, LocalDateTime.class);
//        modelMapper.addConverter(toStringDate);
//        modelMapper.getTypeMap(String.class, LocalDateTime.class);
//
//        return modelMapper;
    }
}
