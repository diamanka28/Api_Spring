package com.openclassroom.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassroom.api.model.*;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

	//méthode de recherche partielle par prénom ou nom
    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
