package com.example.employeeCrud.rest;

import com.example.employeeCrud.enitity.Employee;
import com.example.employeeCrud.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	private EmployeeService employeeService;
	private ObjectMapper objectMapper;

	@Autowired
	public EmployeeRestController(EmployeeService employeeService, ObjectMapper objectMapper) {
		this.employeeService = employeeService;
		this.objectMapper = objectMapper;
	}

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeService.findAll();
	}

	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		Employee foundEmployee = employeeService.findById(employeeId);

		if (foundEmployee == null)
			throw new RuntimeException("Employee id not found - " + employeeId);

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

	@PatchMapping("/employees/{employeeId}")
	public Employee updateEmployee(@PathVariable int employeeId,
								   @RequestBody Map<String, Object> patchPayload) {
		// Find target employee
		Employee tempEmployee = employeeService.findById(employeeId);

		if (tempEmployee == null)
			throw new RuntimeException("Employee id not found - " + employeeId);

		if (patchPayload.containsKey("id"))
			throw new RuntimeException("Employee id field is not allowed in request body - " + employeeId);

		// apply/merge payload changes to target employee
		Employee patchedEmployee = apply(patchPayload, tempEmployee);

		// save updated employee
		return employeeService.save(patchedEmployee);
	}

	private Employee apply(Map<String, Object> patchPayload, Employee tempEmployee) {
		// Convert employee object to json object node
		ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);

		// Convert patchPayload map to json object node
		ObjectNode payloadNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

		// Merge patch partial update(s)
		employeeNode.setAll(payloadNode);

		return objectMapper.convertValue(employeeNode, Employee.class);
	}

	@DeleteMapping("/employees/{employeeId}")
	public void deleteEmployee(@PathVariable int employeeId) {
		employeeService.deleteById(employeeId);

		System.out.println("done");
	}
}
