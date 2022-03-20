package com.example.jsonexercisecardealer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ModelMapper modelMapper(){
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
//
//        modelMapper.createTypeMap(String.class, LocalDateTime.class);
//        modelMapper.addConverter(toStringDate);
//        modelMapper.getTypeMap(String.class, LocalDateTime.class);
//        return modelMapper;
        return new ModelMapper();
    }

    @Bean
    public Gson gson(){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }


}
