package ru.ranepa.service;

import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    @Test
    void shouldCalculateAverageSalary() {
        // Given
        EmployeeRepositoryImpl repository = new EmployeeRepositoryImpl();
        EmployeeService service = new EmployeeService(repository);

        repository.save(new Employee("Ivan", "Developer", 100.0, LocalDate.now()));
        repository.save(new Employee("Maria", "Manager", 200.0, LocalDate.now()));
        repository.save(new Employee("Petr", "QA", 300.0, LocalDate.now()));

        // When
        double result = service.calculateAverageSalary();

        // Then
        assertEquals(200.0, result);
    }

    @Test
    void shouldFindTopEarner() {
        // Given
        EmployeeRepositoryImpl repository = new EmployeeRepositoryImpl();
        EmployeeService service = new EmployeeService(repository);

        repository.save(new Employee("Ivan", "Developer", 100.0, LocalDate.now()));
        repository.save(new Employee("Maria", "Manager", 500.0, LocalDate.now()));

        // When
        Optional<Employee> top = service.findTopEarner();

        // Then
        assertTrue(top.isPresent());
        assertEquals("Maria", top.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenNoEmployees() {
        // Given
        EmployeeRepositoryImpl repository = new EmployeeRepositoryImpl();
        EmployeeService service = new EmployeeService(repository);

        // When
        Optional<Employee> top = service.findTopEarner();

        // Then
        assertTrue(top.isEmpty());
    }
}