package org.ravi.spring.cxf.api;

public class HelloImpl implements Hello {

    @Override
    public String hello(String name) {
        return String.format("Hello World !!! - %s%n", name);
    }
}
