package com.example.finalproject;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FinalProjectApplication {


    @Bean
    public ModelMapper customerModelMapper() {
        return new ModelMapper();
    }
    @Bean
    public ModelMapper customerModelMapperIgnoresNull() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public ModelMapper postModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration();
        return modelMapper;
    }

    @Bean
    public ModelMapper postModelMapperIgnoresNull() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }


    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }

}
