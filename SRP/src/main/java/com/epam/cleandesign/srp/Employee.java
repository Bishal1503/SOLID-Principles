package com.epam.cleandesign.srp;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class Employee {
    EmployeeDAO employeeDao = new EmployeeDAO();
    public synchronized String getAllEmployeesAsHtml(Connection connection) {
        List<EmployeeBO> employees = employeeDao.getEmployeeData(connection);
        StringBuilder builder = new StringBuilder();
        builder.append("<table>").append("<tr><th>Employee</th><th>Position</th></tr>");
        for (EmployeeBO employee : employees) {
            if (employee != null) {
                builder.append("<tr><td>").append(employee.getFirstName()).append(" ").append(employee.getLastName())
                        .append("</td><td>").append(employee.getSeniority()).append(" ").append(employee.getRole())
                        .append("</td></tr>");
            }
        }

        builder.append("</table>");

        return builder.toString();
    }

    public synchronized String employeesAsJson(Connection connection) {
        List<EmployeeBO> employees = employeeDao.getEmployeeData(connection);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(employees);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}