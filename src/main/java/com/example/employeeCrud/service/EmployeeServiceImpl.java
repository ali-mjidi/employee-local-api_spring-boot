package com.example.employeeCrud.service;

import com.example.employeeCrud.dao.EmployeeDAO;
import com.example.employeeCrud.enitity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDAO employeeDAO;

	@Autowired
	public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@Override
	public List<Employee> findAll() {
		return employeeDAO.findAll();
	}
}
