package org.yk.data.mgmt.data.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.yk.data.mgmt.data.entity.Employee;

@Component
public interface EmployeeRepository extends ElasticsearchRepository<Employee,String>{

	Employee queryEmployeeById(String id);
	
}
