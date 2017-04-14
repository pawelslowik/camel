package pl.com.psl.camel.jetty.velocity;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by psl on 14.04.17.
 */
@Configuration
@ComponentScan(basePackageClasses = Config.class)
public class Config extends CamelConfiguration{
}
