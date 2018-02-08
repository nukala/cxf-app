package org.ravi.spring.cxf.api;

import javax.jws.WebService;

@WebService
public interface Hello {
    String hello(String name);
}
