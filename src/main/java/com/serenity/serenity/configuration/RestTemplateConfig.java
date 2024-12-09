package com.serenity.serenity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfig {



    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
     //   DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
      //  defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);
        // /`restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
        return restTemplate;
    }
}
