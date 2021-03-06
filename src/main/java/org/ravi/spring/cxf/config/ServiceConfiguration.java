package org.ravi.spring.cxf.config;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.ravi.spring.cxf.api.HelloImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class ServiceConfiguration {
    public static final String HELLO_SERVICE = "http://localhost:8181/services/hello";

    @Bean
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint endpoint(SpringBus bus) {
        EndpointImpl endpoint = new EndpointImpl(bus, new HelloImpl());
        endpoint.setBus(bus);
        endpoint.publish(HELLO_SERVICE);
        return endpoint;
    }
}
