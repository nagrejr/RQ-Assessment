package com.reliaquest.java_employee_challenge.controller;

import com.reliaquest.java_employee_challenge.dto.Employee;
import com.reliaquest.java_employee_challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController implements IEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeController.class);

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("Fetching all employees");
        List<Employee> employees = new ArrayList<>();
        try {
            employees = employeeService.getAllEmployees();
        } catch (Exception e) {
            logger.error("Error fetching employees: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch employees list", e);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        logger.info("Searching employees with name containing: {}", searchString);
        List<Employee> employees = new ArrayList<>();
        try {
            employees = employeeService.getEmployeesByNameSearch(searchString);
        } catch (Exception e) {
            logger.error("Error searching employees: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to search employees by name", e);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        logger.info("Fetching employee with ID: {}", id);
        Employee employee = new Employee();
        try {
            employee = employeeService.getEmployeeById(id);
        } catch (Exception e) {
            logger.error("Error fetching employee: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch employee by id", e);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        logger.info("Fetching highest salary among employees");
        Integer highestSalary = 0;
        try {
            highestSalary = employeeService.getHighestSalary();
        } catch (Exception e) {
            logger.error("Error fetching highest salary: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch highest salary", e);
        }
        return new ResponseEntity<>(highestSalary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        logger.info("Fetching top ten highest earning employee names");
        List<String> topTenNames = new ArrayList<>();
        try {
            topTenNames = employeeService.getTopTenHighestEarningEmployeeNames();
        } catch (Exception e) {
            logger.error("Error fetching top ten highest earning employee names: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch top ten highest earning employee names", e);

        }
        return new ResponseEntity<>(topTenNames, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> createEmployee(Object employeeInput) {
        logger.info("Create employee");
        Object createdEmployee = new Object();
        try {
            createdEmployee = employeeService.createEmployee(employeeInput);
        } catch (Exception e) {
            logger.error("Error creating employee: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create employee", e);
        }
        return new ResponseEntity<>(createdEmployee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        logger.info("Delete employee with ID: {}", id);
        try {
            boolean isDeleted = employeeService.deleteEmployee(id);
            if (!isDeleted) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
            }
        } catch (ResponseStatusException e) {
            throw e; // Re-throw to preserve the original status and message
        } catch (Exception e) {
            logger.error("Error deleting employee: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete employee by id", e);
        }
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }
}
