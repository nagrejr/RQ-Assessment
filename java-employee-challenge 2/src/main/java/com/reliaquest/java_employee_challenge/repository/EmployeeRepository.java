package com.reliaquest.java_employee_challenge.repository;

import com.reliaquest.java_employee_challenge.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
