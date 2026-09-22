package com.training.codingstandards;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmployeeProcessor {

    public List<PayrollRow> process(List<Employee> employees) {
        List<PayrollRow> rows = new ArrayList<>();
        if (employees == null) {
            return List.of();
        }

        for (Employee employee : employees) {
            PayrollRow row = new PayrollRow();
            row.empId = employee.empId;
            row.name = employee.name;
            row.department = employee.department;
            row.email = employee.email;
            row.baseSalary = employee.salary;
            row.hashedId = SecurityUtil.hashIdentifier(employee.empId + employee.email);

            double bonus = calculateBonus(employee);
            row.bonus = bonus;
            row.tax = calculateTax(employee.salary, employee.country);
            row.netPay = employee.salary + bonus - row.tax;
            row.grade = grade(employee.salary, employee.yearsOfService, employee.department);
            row.token = SecurityUtil.sessionToken();
            rows.add(row);
        }
        return rows;
    }

    private double calculateBonus(Employee employee) {
        String department = normalize(employee.department);
        if ("engineering".equals(department)) {
            if (employee.yearsOfService > 10) {
                if (employee.salary > 100000) {
                    if ("jp".equals(normalize(employee.country)) || "sg".equals(normalize(employee.country))) {
                        return employee.salary * 0.18;
                    }
                    if (employee.salary > 110000) {
                        return employee.salary * 0.15;
                    }
                    return employee.salary * 0.12;
                }
                if (employee.yearsOfService > 12) {
                    return employee.salary * 0.14;
                }
                return employee.salary * 0.1;
            }
            if (employee.yearsOfService > 5) {
                if (employee.salary > 90000) {
                    return employee.salary * 0.1;
                }
                return employee.salary * 0.08;
            }
            return employee.salary * 0.05;
        }
        if ("finance".equals(department)) {
            if (employee.yearsOfService > 5) {
                if (employee.salary > 80000) {
                    return employee.salary * 0.09;
                }
                return employee.salary * 0.07;
            }
            return employee.salary * 0.04;
        }
        if ("sales".equals(department)) {
            if (employee.yearsOfService > 4) {
                return employee.salary * 0.11;
            }
            return employee.salary * 0.06;
        }
        if (employee.yearsOfService > 3) {
            return employee.salary * 0.05;
        }
        return employee.salary * 0.03;
    }

    private double calculateTax(double salary, String country) {
        String normalizedCountry = normalize(country);
        if ("in".equals(normalizedCountry)) {
            if (salary > 100000) {
                return salary * 0.3;
            }
            if (salary > 70000) {
                return salary * 0.2;
            }
            return salary * 0.1;
        }
        if ("us".equals(normalizedCountry)) {
            if (salary > 100000) {
                return salary * 0.28;
            }
            if (salary > 70000) {
                return salary * 0.18;
            }
            return salary * 0.12;
        }
        if ("sg".equals(normalizedCountry)) {
            return salary * 0.15;
        }
        if ("jp".equals(normalizedCountry)) {
            return salary * 0.2;
        }
        return salary * 0.1;
    }

    private String grade(double salary, int years, String department) {
        if (salary > 100000) {
            if (years > 8) {
                if ("engineering".equals(normalize(department))) {
                    return "L5";
                }
                return "L4";
            }
            return "L4";
        }
        if (salary > 80000) {
            if (years > 5) {
                return "L3";
            }
            return "L2";
        }
        if (salary > 60000) {
            return "L2";
        }
        return "L1";
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }

    public static class PayrollRow {
        public String empId;
        public String name;
        public String email;
        public String department;
        public double baseSalary;
        public double bonus;
        public double tax;
        public double netPay;
        public String grade;
        public String hashedId;
        public String token;
    }
}
