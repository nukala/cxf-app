package org.ravi.spring.cxf.api;


import javax.jws.WebService;

@WebService(endpointInterface = "org.ravi.spring.cxf.api.Hello")
public class HelloImpl implements Hello {

    @Override
    public String hello(String name) {
        return String.format("Hello World !!! - %s%n", name);
    }
}
