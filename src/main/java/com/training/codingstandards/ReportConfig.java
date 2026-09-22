package com.training.codingstandards;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class ReportConfig {

    public static final List<Employee> CACHE = new ArrayList<>();

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String OUTPUT_SHEET = "Payroll";
    public static final String OUTPUT_SHEET_2 = "Payroll";

    public static final String DEFAULT_PASSWORD = System.getenv().getOrDefault("APP_ADMIN_PASSWORD", "");

    private ReportConfig() {
    }
}
