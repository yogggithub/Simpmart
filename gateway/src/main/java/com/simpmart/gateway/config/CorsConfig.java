package com.simpmart.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    /**
     * Spring framework has a CORS filter that can handle CORS request
     * Just use this filter and then config it to allow any request sent
     * from any resource through any method to CORS
     *
     * @return a CorsWebFilter
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration =
                new CorsConfiguration();

        // allow any request to CORS
        corsConfiguration.addAllowedHeader("*");
        // allow all methods to CORS
        corsConfiguration.addAllowedMethod("*");
        // allow all request resource to CORS
        corsConfiguration.addAllowedOrigin("*");
        // set CORS request can use cookies
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",
                corsConfiguration);

        return new CorsWebFilter(source);

    }
}
