package com.example.employeeCrud.dao;

import com.example.employeeCrud.enitity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
