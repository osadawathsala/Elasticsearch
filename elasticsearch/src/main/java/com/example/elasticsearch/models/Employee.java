package com.example.elasticsearch.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author Osada
 * @Date 1/20/2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
@Document(indexName = "employee")
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String department;
}
