package com.example.employee.controller;

import com.example.employee.Employee;
import com.example.employee.service.EmployeeService;
import com.example.employee.exception.EmployeeNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.Validator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;
    private final Validator validator;

    public EmployeeController(EmployeeService service, Validator validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(
            @Valid @RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addEmployee(employee));
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = service.getEmployeeById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with id: " + id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable int id,
            @Valid @RequestBody Employee updatedEmployee) {
        Employee employee = service.updateEmployee(id, updatedEmployee)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with id: " + id));
        return ResponseEntity.ok(employee);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> patchEmployee(
            @PathVariable int id,
            @RequestBody Map<String, Object> updates) {

        Employee existing = service.getEmployeeById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with id: " + id));

        Employee candidate = new Employee(
                existing.getId(),
                existing.getName(),
                existing.getAge(),
                existing.getEmail(),
                existing.getDepartment(),
                existing.getSalary()
        );

        if (updates.containsKey("name")) {
            Object value = updates.get("name");
            if (!(value instanceof String)) {
                throw new IllegalArgumentException("Name must be text");
            }
            candidate.setName((String) value);
        }

        if (updates.containsKey("age")) {
            Object value = updates.get("age");
            if (!(value instanceof Number)) {
                throw new IllegalArgumentException("Age must be a number");
            }
            candidate.setAge(((Number) value).intValue());
        }

        if (updates.containsKey("email")) {
            Object value = updates.get("email");
            if (!(value instanceof String)) {
                throw new IllegalArgumentException("Email must be text");
            }
            candidate.setEmail((String) value);
        }

        if (updates.containsKey("department")) {
            Object value = updates.get("department");
            if (!(value instanceof String)) {
                throw new IllegalArgumentException("Department must be text");
            }
            candidate.setDepartment((String) value);
        }

        if (updates.containsKey("salary")) {
            Object value = updates.get("salary");
            if (!(value instanceof Number)) {
                throw new IllegalArgumentException("Salary must be a number");
            }
            candidate.setSalary(((Number) value).doubleValue());
        }

        var errors = validator.validate(candidate);
        if (!errors.isEmpty()) {
            throw new jakarta.validation.ConstraintViolationException(errors);
        }

        Employee updated = service.updateEmployee(id, candidate)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with id: " + id));

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        if (!service.deleteEmployee(id)) {
            throw new EmployeeNotFoundException(
                    "Employee not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/department/{department}")
    public List<Employee> getEmployeesByDepartment(
            @PathVariable String department) {
        return service.getEmployeesByDepartment(department);
    }

    @GetMapping("/sorted-by-salary")
    public List<Employee> getEmployeesSortedBySalary() {
        return service.getEmployeesSortedBySalary();
    }

    @GetMapping("/highest-paid")
    public ResponseEntity<Employee> getHighestPaidEmployee() {
        Employee employee = service.getHighestPaidEmployee()
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "No employees available"));
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/count")
    public Map<String, Long> getEmployeeCount() {
        return Map.of("totalEmployees", service.getEmployeeCount());
    }

    @GetMapping("/average-salary")
    public Map<String, Double> getAverageSalary() {
        return Map.of("averageSalary", service.getAverageSalary());
    }

    @GetMapping("/department-count")
    public Map<String, Long> getDepartmentWiseCount() {
        return service.getDepartmentWiseCount();
    }
}
