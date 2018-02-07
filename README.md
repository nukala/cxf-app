- Simple project - usual hello world, but this time with SOAP via CXF. 


* To run:
mvn spring-boot:run

* Caveats
Without the transports*jetty dependency, we will see the HttpDestinationFactory exceptions.


- To see WSDL: 
browse to http://localhost:8181/services/hello/?wsdl
