package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class HRMApplication {

    public static void main(String[] args) {
        EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl();
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== HRM System Menu ===");
            System.out.println("1. Show all employees");
            System.out.println("2. Add employee");
            System.out.println("3. Delete employee");
            System.out.println("4. Find employee by ID");
            System.out.println("5. Show statistics");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (choice == 1) {
                Iterable<Employee> all = employeeRepository.findAll();
                boolean empty = true;
                for (Employee e : all) {
                    System.out.println(e);
                    empty = false;
                }
                if (empty) System.out.println("No employees found.");

            } else if (choice == 2) {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter position: ");
                String position = scanner.nextLine();
                try {
                    System.out.print("Enter salary: ");
                    double salary = Double.parseDouble(scanner.nextLine().trim());
                    Employee emp = new Employee(name, position, salary, LocalDate.now());
                    System.out.println(employeeRepository.save(emp));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid salary. Employee not added.");
                }

            } else if (choice == 3) {
                System.out.print("Enter ID to delete: ");
                try {
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    System.out.println(employeeRepository.delete(id));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID.");
                }

            } else if (choice == 4) {
                System.out.print("Enter ID to find: ");
                try {
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    Optional<Employee> found = employeeRepository.findById(id);
                    if (found.isPresent()) {
                        System.out.println("Found: " + found.get());
                    } else {
                        System.out.println("Employee not found.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID.");
                }

            } else if (choice == 5) {
                System.out.println("Average salary: " + employeeService.calculateAverageSalary());
                Optional<Employee> top = employeeService.findTopEarner();
                if (top.isPresent()) {
                    System.out.println("Top earner: " + top.get());
                } else {
                    System.out.println("No employees yet.");
                }

            } else if (choice == 6) {
                System.out.println("Goodbye!");
                break;

            } else {
                System.out.println("Unknown option. Try again.");
            }
        }
    }
}