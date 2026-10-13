package com.example.employee.service;

import com.example.employee.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    // Add employee
    public Employee addEmployee(Employee employee) {
        employee.setId(idGenerator.getAndIncrement());
        employees.add(employee);
        return employee;
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    // Get employee by ID
    public Optional<Employee> getEmployeeById(int id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst();
    }

    // Update employee
    public Optional<Employee> updateEmployee(int id, Employee updatedEmployee) {
        return getEmployeeById(id).map(employee -> {
            employee.setName(updatedEmployee.getName());
            employee.setAge(updatedEmployee.getAge());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setSalary(updatedEmployee.getSalary());
            return employee;
        });
    }

    // Delete employee
    public boolean deleteEmployee(int id) {
        return employees.removeIf(employee -> employee.getId() == id);
    }

    // Get employees by department
    public List<Employee> getEmployeesByDepartment(String department) {
        return employees.stream()
                .filter(employee -> employee.getDepartment() != null
                        && employee.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    // Sort employees by salary (highest first)
    public List<Employee> getEmployeesSortedBySalary() {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .collect(Collectors.toList());
    }

    // Get highest-paid employee
    public Optional<Employee> getHighestPaidEmployee() {
        return employees.stream()
                .max(Comparator.comparing(Employee::getSalary));
    }

    // Count employees
    public long getEmployeeCount() {
        return employees.stream().count();
    }

    // Average salary
    public double getAverageSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    // Count employees in each department
    public Map<String, Long> getDepartmentWiseCount() {
        return employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()));
    }
}

