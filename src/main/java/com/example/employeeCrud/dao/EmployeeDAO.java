package com.example.employeeCrud.dao;

import com.example.employeeCrud.enitity.Employee;

import java.util.List;

public interface EmployeeDAO {
	// Create - Post
	Employee save(Employee theEmployee);

	// Read - Get
	List<Employee> findAll();

	Employee findById(int id);

	// Update - Put
	// Use the save() method to insert and update data

	// Delete - Delete
	void deleteById(int id);

}
