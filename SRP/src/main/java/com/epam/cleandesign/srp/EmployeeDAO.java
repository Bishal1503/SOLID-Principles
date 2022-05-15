package com.epam.cleandesign.srp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private List<EmployeeBO> cache = null;

    public List<EmployeeBO> getEmployeeData(Connection connection) {
        if (cache == null) {
            cache = new ArrayList<>();
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM Employees")) {

                while (resultSet.next()) {

                    EmployeeBO employeeBO = new EmployeeBO("Jim", "Robbins", EmployeeRole.SOFTWARE_ENGINEER, EmployeeSeniority.REGULAR);

                    employeeBO.setFirstName(resultSet.getString("FIRST_NAME"));
                    employeeBO.setLastName(resultSet.getString("LAST_NAME"));
                    employeeBO.setRole(EmployeeRole.valueOf(resultSet.getString("ROLE")));
                    employeeBO.setSeniority(EmployeeSeniority.valueOf(resultSet.getString("SENIORITY")));
                    cache.add(employeeBO);
                }
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }

        }
        return cache;
    }

}
