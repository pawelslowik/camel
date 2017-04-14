package pl.com.psl.camel.jetty.velocity;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by psl on 14.04.17.
 */
@Component
public class Routes extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(Routes.class);

    @Autowired
    private EmployeesRequestProcessor employeesRequestProcessor;
    @Autowired
    private Environment environment;
    private Integer port;

    @PostConstruct
    public void init() {
        port = environment.getProperty("jetty.port", Integer.class, 8081);
        LOGGER.info("Using Jetty port={}", port);
    }

    public void configure() throws Exception {
        restConfiguration()
                .component("jetty")
                .scheme("http")
                .port(port);

        rest("/employees")
                .get()
                .produces("text/html")
                .route()
                .routeId("employees-route")
                .process(employeesRequestProcessor)
                .end();
    }
}
