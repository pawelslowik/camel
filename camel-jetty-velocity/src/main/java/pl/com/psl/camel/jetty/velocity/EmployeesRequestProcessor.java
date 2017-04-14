package pl.com.psl.camel.jetty.velocity;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by psl on 14.04.17.
 */
@Component
public class EmployeesRequestProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesRequestProcessor.class);
    private static final String HTML_TEMPLATE = "employees.vm";
    private final VelocityEngine velocityEngine = new VelocityEngine();


    public EmployeesRequestProcessor() {
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
    }

    public void process(Exchange exchange) throws Exception {
        LOGGER.info("Started request processing");
        List<Employee> employees = getEmployees();
        exchange.getOut().setBody(toHtml(employees));
        LOGGER.info("Got employees={}", employees);
        LOGGER.info("Finished request processing");
    }

    private String toHtml(List<Employee> employees) throws IOException {
        LOGGER.info("Converting employees list to html format");
        VelocityContext velocityContext = new VelocityContext();
        List<Map<String, String>> employeesParametersList = employees.stream()
                .map( e -> getEmployeeParameters(e))
                .collect(Collectors.toList());
        velocityContext.put("employees", employeesParametersList);
        Template template = velocityEngine.getTemplate(HTML_TEMPLATE);
        try(StringWriter stringWriter = new StringWriter()){
            template.merge(velocityContext, stringWriter);
            String html = stringWriter.toString();
            LOGGER.info("Converted employees list to html={}", html);
            return html;
        }
    }

    private Map<String, String> getEmployeeParameters(Employee employee){
        Map<String, String> employeeParametersMap = new HashMap<>();
        employeeParametersMap.put("name", employee.getName());
        employeeParametersMap.put("role", employee.getRole().name());
        employeeParametersMap.put("hireDate", String.valueOf(employee.getHireDate()));
        employeeParametersMap.put("salary", employee.getSalary().toPlainString());
        return employeeParametersMap;
    }

    private List<Employee> getEmployees(){
        Employee tom = new Employee("tom", LocalDate.now(), BigDecimal.valueOf(100), Employee.Role.MANAGER);
        Employee bob = new Employee("bob", LocalDate.now().minusDays(1), BigDecimal.valueOf(70.5), Employee.Role.DEVELOPER);
        Employee pat = new Employee("pat", LocalDate.now().minusDays(2), BigDecimal.valueOf(70), Employee.Role.TESTER);
        Employee tim = new Employee("tim", LocalDate.now().minusDays(2), BigDecimal.valueOf(95.5), Employee.Role.DEVELOPER);
        return Arrays.asList(tom, bob, pat, tim);
    }
}
