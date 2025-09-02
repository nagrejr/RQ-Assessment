package com.reliaquest.api.service;

import com.reliaquest.api.dto.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    List<Employee> getEmployeesByNameSearch(String searchString);

    Employee getEmployeeById(String id);

    Integer getHighestSalary();

    List<String> getTopTenHighestEarningEmployeeNames();

    Object createEmployee(Object employeeInput);

    Boolean deleteEmployee(Object deletedEmployee);
}
