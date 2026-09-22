package com.training.codingstandards;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvEmployeeReader {

    public List<Employee> read(String csvPath) {
        List<Employee> employees = new ArrayList<Employee>();
        try {
            InputStream inputStream;
            if (csvPath == null) {
                inputStream = CsvEmployeeReader.class.getResourceAsStream("/employees.csv");
            } else {
                inputStream = new FileInputStream(csvPath);
            }

            CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(inputStream));
            for (CSVRecord record : parser) {
                Employee employee = new Employee();
                employee.empId = record.get("empId");
                employee.name = record.get("name");
                employee.email = record.get("email");
                employee.department = record.get("department");
                employee.salary = Double.parseDouble(record.get("salary"));
                employee.yearsOfService = Integer.parseInt(record.get("yearsOfService"));
                employee.country = record.get("country");
                employee.managerEmail = record.get("managerEmail");
                employees.add(employee);
                ReportConfig.CACHE.add(employee);
                System.out.println("Loaded employee " + employee.name + " email=" + employee.email);
            }
        } catch (Exception e) {
        }
        return employees;
    }
}
