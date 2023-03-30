package com.onionit.ebank.configurations;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.onionit.ebank.controllers.annotations.HealthController;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(@NotNull PathMatchConfigurer configurer) {
        configurer.addPathPrefix("health", HandlerTypePredicate.forAnnotation(HealthController.class));
        configurer.addPathPrefix("resources", HandlerTypePredicate.forAnnotation(ResourcesController.class));
    }

    @Override
    public void extendMessageConverters(final @NotNull List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> httpMessageConverter : converters) {
            if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter converter) {
                this.configureMappingJackson2HttpMessageConverter(converter);
            }
        }
    }

    protected void configureMappingJackson2HttpMessageConverter(@NotNull MappingJackson2HttpMessageConverter converter) {
        this.configureObjectMapper(converter.getObjectMapper());
    }

    protected void configureObjectMapper(@NotNull ObjectMapper mapper) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //noinspection deprecation
        mapper.enable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }
}
