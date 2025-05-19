package com.example.employeeCrud.rest;

import com.example.employeeCrud.enitity.Employee;
import com.example.employeeCrud.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

	private final EmployeeService employeeService;
	private final ObjectMapper objectMapper;

	@Autowired
	public EmployeeRestController(EmployeeService employeeService, ObjectMapper objectMapper) {
		this.employeeService = employeeService;
		this.objectMapper = objectMapper;
	}

	// Get all employees
	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeService.findAll();
	}

	// Get one employee by ID
	@GetMapping("/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		Employee foundEmployee = employeeService.findById(employeeId);

		if (foundEmployee == null)
			throw new EmployeeNotFoundException(employeeId);

		return foundEmployee;
	}

	// Add new employee
	@PostMapping
	public Employee addEmployee(@RequestBody Employee newEmployee) {
		// In the case of client sends id in the request body, set id to 0
		newEmployee.setId(0); // to force insert instead of update

		return employeeService.save(newEmployee);
	}

	// Full update of employee
	@PutMapping
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		return employeeService.save(theEmployee);
	}

	// Partial update of employee using PATCH
	@PatchMapping("/{employeeId}")
	public Employee updateEmployee(@PathVariable int employeeId,
								   @RequestBody Map<String, Object> patchPayload) {

		// Find target employee
		Employee tempEmployee = employeeService.findById(employeeId);

		if (tempEmployee == null)
			throw new EmployeeNotFoundException(employeeId);

		// Block id update via patch
		if (patchPayload.containsKey("id"))
			throw new RuntimeException("Employee id field is not allowed in request body - " + employeeId);

		// Apply/merge payload changes to target employee
		Employee patchedEmployee = applyPatch(patchPayload, tempEmployee);

		// Save updated employee
		return employeeService.save(patchedEmployee);
	}

	// Helper method to apply PATCH fields into existing employee
	private Employee applyPatch(Map<String, Object> patchPayload, Employee tempEmployee) {
		// Convert employee object to json object node
		ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);

		// Convert patchPayload map to json object node
		ObjectNode payloadNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

		// Merge patch partial update(s)
		employeeNode.setAll(payloadNode);

		// Convert back to Employee object
		return objectMapper.convertValue(employeeNode, Employee.class);
	}

	// Delete employee by ID
	@DeleteMapping("/{employeeId}")
	public Map<String, Object> deleteEmployee(@PathVariable int employeeId) {
		if (employeeService.findById(employeeId) == null) {
			throw new EmployeeNotFoundException(employeeId);
		}

		employeeService.deleteById(employeeId);

		return Map.ofEntries(
				Map.entry("status", HttpStatus.OK.value()),
				Map.entry("message", "Deleted employee id - " + employeeId)
		);
	}
}
