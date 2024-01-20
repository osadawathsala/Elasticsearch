package com.example.elasticsearch.repositories;

import com.example.elasticsearch.models.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Osada
 * @Date 1/20/2024
 */
public interface EmployeeRepository extends ElasticsearchRepository<Employee,String> {
}
