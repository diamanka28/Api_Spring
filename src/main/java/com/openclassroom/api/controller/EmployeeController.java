package com.openclassroom.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.api.model.Employee;
import com.openclassroom.api.service.EmployeeService;


@CrossOrigin(origins= "http://localhost:4200")
@Controller
@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	/**
    * Read - Get all employees

    * @return - An Iterable object of Employee full filled

    */
	
	@GetMapping("/")
	public String home(Model model) {
        Iterable<Employee> employees = employeeService.getEmployees();
        model.addAttribute("employees", employees);
        return "home"; 
    }
	
	@GetMapping("/employees")
	public Iterable<Employee> getEmployees(){
		Iterable<Employee> employees = employeeService.getEmployees();
        employees.forEach(System.out::println);
        return employees; 
	}
	
	@GetMapping("/employees/{id}")
	public Optional<Employee> getEmployee(@PathVariable("id") final long id) {
		Optional<Employee> e = employeeService.getEmployee(id);
		return e;
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") long id) {
	    Optional<Employee> e = employeeService.getEmployee(id);
	    if (e.isPresent()) {
	        employeeService.deleteEmployee(id);
	        return ResponseEntity.noContent().build(); // 204 No Content
	    } else {
	        return ResponseEntity.notFound().build(); // 404 Not Found
	    }
	}

	@PostMapping("/auth")
	public ResponseEntity<Map<String, Object>> authentificate(@RequestBody Map<String, String> request){
		String mail = request.get("mail");
		String password = request.get("password");
		
		Optional<Employee> empOpt = StreamSupport.stream(employeeService.getEmployees().spliterator(), false)
		        .filter(emp -> emp.getMail().equals(mail) &&
	                       emp.getPassword().equals(password))
	        .findFirst();
		
		if(empOpt.isPresent()) {
			Employee emp = empOpt.get();
	        Map<String,Object> body= new HashMap<>();
	        body.put("token", "fake-jwt-token-" + empOpt.get().getId());
	        body.put("user", emp);
	        return ResponseEntity.ok(body);
	    }
	    return ResponseEntity.status(401).body(Map.of("error", "Identifiants invalides"));
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
	    Optional<Employee> empOpt = employeeService.getEmployee(id);
	    if (empOpt.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    Employee existingEmp = empOpt.get();
	    existingEmp.setFirstName(employee.getFirstName());
	    existingEmp.setLastName(employee.getLastName());
	    existingEmp.setMail(employee.getMail());
	    existingEmp.setPassword(employee.getPassword());
	    Employee savedEmp = employeeService.saveEmployee(existingEmp);
	    return ResponseEntity.ok(savedEmp);
	}

	
}
