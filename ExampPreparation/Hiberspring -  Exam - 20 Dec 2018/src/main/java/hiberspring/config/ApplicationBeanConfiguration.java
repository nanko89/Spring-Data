package hiberspring.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hiberspring.util.*;
import hiberspring.util.impl.FileUtilImpl;
import hiberspring.util.impl.ValidationUtilImpl;
import hiberspring.util.impl.XmlParserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
