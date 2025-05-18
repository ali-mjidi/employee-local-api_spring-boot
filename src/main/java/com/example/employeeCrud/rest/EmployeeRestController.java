package com.example.employeeCrud.rest;

import com.example.employeeCrud.enitity.Employee;
import com.example.employeeCrud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	private EmployeeService employeeService;

	@Autowired
	public EmployeeRestController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeService.findAll();
	}

	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		Employee foundEmployee = employeeService.findById(employeeId);

		if (foundEmployee == null)
			throw new RuntimeException("Employee with id " + employeeId + " hasn't found");

		return foundEmployee;
	}

	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee newEmployee) {
		// In the case of client send id in the request body, set id to 0
		newEmployee.setId(0);

		Employee dbEmployee = employeeService.save(newEmployee);

		return dbEmployee;
	}

	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		Employee dbEmployee = employeeService.save(theEmployee);

		return dbEmployee;
	}

	@DeleteMapping("/employees/{employeeID}")
	public void deleteEmployee(@PathVariable int employeeID) {
		employeeService.deleteById(employeeID);

		System.out.println("done");
	}
}
