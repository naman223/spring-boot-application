package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Handles requests for the Employee service.
 */
@RestController(value = "/")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    //Map to store employees, ideally we should use database
    Map<Integer, Employee> empData = new HashMap<Integer, Employee>();

    @GetMapping(value = EmpRestURIConstants.GET_EMP)
    public @ResponseBody Employee getEmployee(@PathVariable("id") int empId) {
        logger.info("Start getEmployee. ID="+empId);
        return empData.get(empId);
    }

    @GetMapping(value = EmpRestURIConstants.GET_ALL_EMP)
    public @ResponseBody List<Employee> getAllEmployees() {
        logger.info("Start getAllEmployees.");
        List<Employee> emps = new ArrayList<Employee>();
        Set<Integer> empIdKeys = empData.keySet();
        for(Integer i : empIdKeys){
            emps.add(empData.get(i));
        }
        return emps;
    }

    @PostMapping(value = EmpRestURIConstants.CREATE_EMP)
    public @ResponseBody Employee createEmployee(@RequestBody Employee emp) {
        logger.info("Start createEmployee.");
        emp.setCreatedDate(new Date());
        empData.put(emp.getId(), emp);
        return emp;
    }

    @DeleteMapping(value = EmpRestURIConstants.DELETE_EMP)
    public @ResponseBody Employee deleteEmployee(@PathVariable("id") int empId) {
        logger.info("Start deleteEmployee.");
        Employee emp = empData.get(empId);
        empData.remove(empId);
        return emp;
    }
}
