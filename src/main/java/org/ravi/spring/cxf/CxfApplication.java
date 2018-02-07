package org.ravi.spring.cxf;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class CxfApplication {
    public static void main(String ... args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .build()
                .run(CxfApplication.class, args);
    }
}
