package com.example.employeeCrud.dao;

import com.example.employeeCrud.enitity.Employee;

import java.util.List;

public interface EmployeeDAO {
	List<Employee> findAll();
}
