package pl.com.psl.camel.jetty.velocity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by psl on 14.04.17.
 */
public class Employee {

    public enum Role { DEVELOPER, MANAGER, TESTER}

    private String name;
    private LocalDate hireDate;
    private BigDecimal salary;
    private Role role;

    public Employee(String name, LocalDate hireDate, BigDecimal salary, Role role) {
        this.name = name;
        this.hireDate = hireDate;
        this.salary = salary;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", hireDate=" + hireDate +
                ", salary=" + salary +
                ", role=" + role +
                '}';
    }
}
