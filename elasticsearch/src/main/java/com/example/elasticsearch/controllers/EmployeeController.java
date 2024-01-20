package com.example.elasticsearch.controllers;

import com.example.elasticsearch.models.Employee;
import com.example.elasticsearch.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Osada
 * @Date 1/20/2024
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public Employee create(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
    @GetMapping("/{id}")
    public Optional<Employee> findById(@PathVariable("id") String id){
        return employeeRepository.findById(id);
    }
    @GetMapping
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        employeeRepository.deleteById(id);
    }

}
