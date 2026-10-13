package com.example.employee;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Employee {

    private Integer id;

    @NotBlank(message = "Employee name is required")
    @Size(min = 3, max = 50,
          message = "Name must be between 3 and 50 characters")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Employee age must be at least 18")
    @Max(value = 60, message = "Employee age cannot exceed 60")
    private Integer age;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Size(max = 50, message = "Email cannot exceed 50 characters")
    private String email;

    @NotBlank(message = "Department is required")
    @Size(min = 2, max = 30,
          message = "Department must be between 2 and 30 characters")
    private String department;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than zero")
    @DecimalMax(value = "1000000.00",
                message = "Salary cannot exceed 1000000")
    private Double salary;

    public Employee() {
    }

    public Employee(Integer id, String name, Integer age,
                    String email, String department, Double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id
                + ", name=" + name
                + ", age=" + age
                + ", email=" + email
                + ", department=" + department
                + ", salary=" + salary + "]";
    }
}