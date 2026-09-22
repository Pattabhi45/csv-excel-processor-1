package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeProcessorTest {

    @Test
    void processCreatesPayrollRowForEachEmployee() {
        Employee employee = new Employee("1001", "Asha Raman", "asha.raman@example.com",
                "Engineering", 92000, 6, "IN", "lead.eng@example.com");

        EmployeeProcessor processor = new EmployeeProcessor();
        List<EmployeeProcessor.PayrollRow> rows = processor.process(Arrays.asList(employee));

        assertNotNull(rows);
        assertEquals(1, rows.size());
        assertEquals("1001", rows.get(0).empId);
        assertEquals("Engineering", rows.get(0).department);
    }

    @Test
    void processUsesStringValueComparisonForDepartmentCountryAndGrade() {
        Employee employee = new Employee("1002", "Kavya", "kavya@example.com",
                new String("Engineering"), 95000, 10, new String("US"), "manager@example.com");

        EmployeeProcessor processor = new EmployeeProcessor();
        List<EmployeeProcessor.PayrollRow> rows = processor.process(Arrays.asList(employee));

        assertNotNull(rows);
        assertEquals(1, rows.size());
        assertEquals(9500.0, rows.get(0).bonus);
        assertEquals(17100.0, rows.get(0).tax);
        assertEquals(95000 + 9500 - 17100, rows.get(0).netPay, 0.0001);
        assertEquals("L3", rows.get(0).grade);
    }
}
