package com.openclassroom.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassroom.api.repository.EmployeeRepository;

import lombok.Data;

import com.openclassroom.api.model.*;

@Data
@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public Optional<Employee> getEmployee(long id){
		return employeeRepository.findById(id);
	}
	
	public Iterable<Employee> getEmployees(){
		return employeeRepository.findAll();
	}
	
	public void deleteEmployee(final Long id) {
		employeeRepository.deleteById(id);
	}
	
	public Employee saveEmployee(Employee employee) {
		Employee savedEmployee = employeeRepository.save(employee);
		return savedEmployee;
		
	}
}
