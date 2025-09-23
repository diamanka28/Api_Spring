package com.openclassroom.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassroom.api.model.*;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
