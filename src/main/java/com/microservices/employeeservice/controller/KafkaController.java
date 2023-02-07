package com.microservices.employeeservice.controller;

import com.microservices.employeeservice.db.entity.EmployeeEntity;
import com.microservices.employeeservice.kafka.EmployeeProducer;
import com.microservices.employeeservice.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private EmployeeProducer employeeProducer;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/publish/{empId}")
    public void publish(@PathVariable String empId) {
        Optional<EmployeeEntity> emp = employeeService.getEmployeeById(empId);
        employeeProducer.sendMessage(emp.orElseThrow());
        LOG.info("Employee id '" + empId + "'published");
    }
}
