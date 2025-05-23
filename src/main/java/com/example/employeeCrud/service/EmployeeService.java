package com.example.employeeCrud.service;

import com.example.employeeCrud.enitity.Employee;

import java.util.List;

public interface EmployeeService {
	List<Employee> findAll();

	Employee findById(int id);

	Employee save(Employee theEmployee);

	void deleteById(int id);
}
