package com.silverhyuk.currencyconverter.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * [Spring Web MVC 설정]
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //static 위치의 리소스들을 활용하기 위함..
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").setCachePeriod(20);
    }

    //Cors 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/currency/getCurrencyRate")
                .allowedMethods(HttpMethod.GET.name())
                .allowedOrigins("*");

        registry.addMapping("/currency/getAmountReceived")
                .allowedMethods(HttpMethod.POST.name())
                .allowedOrigins("*");
    }
}
