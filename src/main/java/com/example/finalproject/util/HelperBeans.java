package com.example.finalproject.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelperBeans {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
