package com.reliaquest.java_employee_challenge.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.java_employee_challenge.dto.Employee;
import com.reliaquest.java_employee_challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final WebClient webClient;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<Employee> getAllEmployees() {
        logger.debug("Fetching all employees");
        try {
            JsonNode data = webClient.get()
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block()
                    .get("data");
            ObjectMapper mapper = new ObjectMapper();
            return Arrays.asList(mapper.treeToValue(data, Employee[].class));
        } catch (Exception e) {
            logger.error("Error fetching all employees: {}", e.getMessage());
            throw new RuntimeException("Unable to fetch employees", e);
        }
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        logger.debug("getEmployeesByNameSearch method called with searchString: {}", searchString);
        return getAllEmployees().stream()
                .filter(emp -> emp.getName() != null && emp.getName().toLowerCase().contains(searchString.toLowerCase()))
                .toList();
    }

    @Override
    public Employee getEmployeeById(String id) {
        logger.debug("getEmployeeById method called with id: {}", id);
        try {
            JsonNode data = webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block()
                    .get("data");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.treeToValue(data, Employee.class);
        } catch (Exception e) {
            logger.error("Error fetching employee by ID: {}", e.getMessage());
            throw new RuntimeException("Unable to fetch employee by ID", e);
        }
    }

    @Override
    public Integer getHighestSalary() {
        logger.debug("getHighestSalary method called");
        return getAllEmployees().stream()
                .map(Employee::getSalary)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0);
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        logger.debug("getTopTenHighestEarningEmployeeNames method called");
        return getAllEmployees().stream()
                .sorted(Comparator.comparing(Employee::getSalary, Comparator.reverseOrder()))
                .limit(10)
                .map(Employee::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Object createEmployee(Object employeeInput) {
        logger.debug("createEmployee method called with input: {}", employeeInput);
        try {
            JsonNode data = webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(employeeInput)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block()
                    .get("data");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.treeToValue(data, Employee.class);
        } catch (Exception e) {
            logger.error("Error creating employee: {}", e.getMessage());
            throw new RuntimeException("Unable to create employee", e);
        }
    }

    @Override
    public Boolean deleteEmployee(Object deletedEmployee) {
        logger.debug("deleteEmployee method called with input: {}", deletedEmployee);
        try {
            String employeeName = (String) deletedEmployee;
            JsonNode data = webClient.delete()
                    .uri("/{name}", employeeName)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block()
                    .get("data");
            return data.asBoolean();
        } catch (Exception e) {
            logger.error("Error deleting employee: {}", e.getMessage());
            //throw new RuntimeException("Unable to delete employee", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to delete employee", e);
        }
    }
}
