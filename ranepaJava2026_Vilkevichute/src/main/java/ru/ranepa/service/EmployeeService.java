package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.util.Optional;

public class EmployeeService {

    private EmployeeRepositoryImpl employeeRepository;

    public EmployeeService(EmployeeRepositoryImpl employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public double calculateAverageSalary() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        double sumSalary = 0;
        int count = 0;
        for (Employee employee : allEmployees) {
            sumSalary += employee.getSalary();
            count++;
        }
        if (count == 0) return 0;
        return sumSalary / count;
    }

    public Optional<Employee> findTopEarner() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        Employee top = null;
        for (Employee employee : allEmployees) {
            if (top == null || employee.getSalary() > top.getSalary()) {
                top = employee;
            }
        }
        return Optional.ofNullable(top);
    }
}