package org.ravi.spring.cxf;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ravi.spring.cxf.api.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.ravi.spring.cxf.config.ServiceConfiguration.HELLO_SERVICE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HelloIT.TestCfg.class})
public class HelloIT {
    private final Logger logger = LoggerFactory.getLogger(HelloIT.class);

    @Autowired
    @Qualifier("client")
    private Hello hello;


    @Test
    public void sayHello() {
        String name = "ravi";

        String answer = hello.hello(name);
        logger.info("Response from server = [{}]", answer);
        assertThat("contains name=[" + name + "]", answer, containsString(name));
    }

    @Configuration
    static class TestCfg {

        @Bean(name = "client")
        public Hello generateProxy(@Qualifier("helloFactoryBean") JaxWsProxyFactoryBean factoryBean) {
            Object bean = factoryBean.create();
            return (Hello ) bean;
        }

        @Bean
        public JaxWsProxyFactoryBean helloFactoryBean() {
            JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
            factoryBean.setServiceClass(Hello.class);
            factoryBean.setAddress(HELLO_SERVICE);
            return factoryBean;
        }
    }
}
