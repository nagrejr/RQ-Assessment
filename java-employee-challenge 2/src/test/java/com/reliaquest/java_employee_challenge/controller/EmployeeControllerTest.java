package com.reliaquest.java_employee_challenge.controller;

import com.reliaquest.java_employee_challenge.dto.Employee;
import com.reliaquest.java_employee_challenge.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void getAllEmployeesReturnsListOfEmployees() {
        List<Employee> mockEmployees = List.of(new Employee(1, "John Doe", 5000, 25, "SDE", "john@gmail.com"));
        Mockito.when(employeeService.getAllEmployees()).thenReturn(mockEmployees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEmployees, response.getBody());
    }

    @Test
    void getAllEmployeesThrowsInternalServerErrorOnException() {
        Mockito.when(employeeService.getAllEmployees()).thenThrow(new RuntimeException("Database error"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> employeeController.getAllEmployees());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Unable to fetch employees list", exception.getReason());
    }

    @Test
    void getEmployeeByIdReturnsEmployee() {
        Employee mockEmployee = new Employee(1, "John Doe", 5000, 25, "SDE", "john@gmail.com");
        Mockito.when(employeeService.getEmployeeById("1")).thenReturn(mockEmployee);

        ResponseEntity<Employee> response = employeeController.getEmployeeById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEmployee, response.getBody());
    }

    @Test
    void getEmployeeByIdThrowsInternalServerErrorOnException() {
        Mockito.when(employeeService.getEmployeeById("1")).thenThrow(new RuntimeException("Database error"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> employeeController.getEmployeeById("1"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Unable to fetch employee by id", exception.getReason());
    }

    @Test
    void createEmployeeReturnsCreatedEmployee() {
        Object input = new Employee(2, "Luke Shaw", 2000, 30, "SDE", "luke@gmail.com");
        Object createdEmployee = new Employee(2, "Luke Shaw", 2000, 30, "SDE", "luke@gmail.com");
        Mockito.when(employeeService.createEmployee(input)).thenReturn(createdEmployee);

        ResponseEntity<Object> response = employeeController.createEmployee(input);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdEmployee, response.getBody());
    }

    @Test
    void createEmployeeThrowsInternalServerErrorOnException() {
        Object input = new Employee(1, "John Doe", 5000, 25, "SDE", "john@gmail.com");
        Mockito.when(employeeService.createEmployee(input)).thenThrow(new RuntimeException("Database error"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> employeeController.createEmployee(input));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Unable to create employee", exception.getReason());
    }

//    @Test
//    void deleteEmployeeByIdReturnsSuccessMessage() {
//        Object deletedEmployee = "John Doe";
//        Mockito.when(employeeService.deleteEmployee(deletedEmployee)).thenReturn(true);
//
//        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Employee deleted successfully", response.getBody());
//    }

    @Test
    void deleteEmployeeByIdReturnsSuccessWhenEmployeeIsDeleted() {
        Mockito.when(employeeService.deleteEmployee("1")).thenReturn(true);

        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee deleted successfully", response.getBody());
    }

    @Test
    void deleteEmployeeByIdThrowsNotFoundWhenEmployeeDoesNotExist() {
        Mockito.when(employeeService.deleteEmployee("1")).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> employeeController.deleteEmployeeById("1"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Employee not found", exception.getReason());
    }

    @Test
    void deleteEmployeeByIdThrowsInternalServerErrorOnException() {
        Object deletedEmployee = "John Doe";
        Mockito.when(employeeService.deleteEmployee(deletedEmployee)).thenThrow(new RuntimeException("Database error"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> employeeController.deleteEmployeeById("1"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Unable to delete employee by id", exception.getReason());
    }
}