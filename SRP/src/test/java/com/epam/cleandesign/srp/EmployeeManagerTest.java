package com.epam.cleandesign.srp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.mail.Message;
import javax.mail.Transport;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Transport.class)
public class EmployeeManagerTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private ResultSet resultSetMock;

    @Before
    public void init() throws SQLException {
        MockitoAnnotations.initMocks(this);
        final Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(any())).thenReturn(resultSetMock);
    }

    @Test
    public void emptyJson() throws Exception {
        setUpResultSetMock(Collections.emptyList());
        testJsonConvert("[]");
    }

    @Test
    public void singleEmployeeJson() throws Exception {
        setUpResultSetMock(Collections.singletonList(
                new EmployeeBO("Jim", "Robbins", EmployeeRole.SOFTWARE_ENGINEER, EmployeeSeniority.REGULAR)));
        testJsonConvert(
                "[{\"firstName\":\"Jim\",\"lastName\":\"Robbins\",\"role\":\"SOFTWARE_ENGINEER\",\"seniority\":\"REGULAR\"}]");
    }

    @Test
    public void multipleEmployeesJson() throws Exception {
        setUpResultSetMock(
                Arrays.asList(new EmployeeBO("Jim", "Robbins", EmployeeRole.SOFTWARE_ENGINEER, EmployeeSeniority.REGULAR),
                              new EmployeeBO("Tom", "Hanks", EmployeeRole.SOFTWARE_TEST_AUTOMATION_ENGINEER,
                                           EmployeeSeniority.SENIOR
                              )
                ));
        testJsonConvert(
                "[{\"firstName\":\"Jim\",\"lastName\":\"Robbins\",\"role\":\"SOFTWARE_ENGINEER\",\"seniority\":\"REGULAR\"}," +
                "{\"firstName\":\"Tom\",\"lastName\":\"Hanks\",\"role\":\"SOFTWARE_TEST_AUTOMATION_ENGINEER\",\"seniority\":\"SENIOR\"}]");
    }

    @Test
    public void sendEmptyEmployeesReport() throws Exception {
        setUpResultSetMock(Collections.emptyList());
        testSendMail("<table>" + "<tr><th>Employee</th><th>Position</th></tr>" + "</table>");
    }

    @Test
    public void sendSingleEmployeeReport() throws Exception {
        setUpResultSetMock(Collections.singletonList(
                new EmployeeBO("Annie", "Shaw", EmployeeRole.RESOURCE_MANAGER, EmployeeSeniority.CHIEF)));
        testSendMail("<table>" + "<tr><th>Employee</th><th>Position</th></tr>" +
                     "<tr><td>Annie Shaw</td><td>CHIEF RESOURCE_MANAGER</td></tr>" + "</table>");
    }

    @Test
    public void sendMultipleEmployeesReport() throws Exception {
        setUpResultSetMock(
                Arrays.asList(new EmployeeBO("Charlie", "Smith", EmployeeRole.SOFTWARE_TEST_AUTOMATION_ENGINEER, EmployeeSeniority.LEAD),
                              new EmployeeBO("Larry", "Cage", EmployeeRole.PROJECT_MANAGER,
                                           EmployeeSeniority.CHIEF
                              )
                ));
        testSendMail("<table>" + "<tr><th>Employee</th><th>Position</th></tr>" +
                     "<tr><td>Charlie Smith</td><td>LEAD SOFTWARE_TEST_AUTOMATION_ENGINEER</td></tr>" +
                     "<tr><td>Larry Cage</td><td>CHIEF PROJECT_MANAGER</td></tr>" + "</table>");
    }

    private void setUpResultSetMock(List<EmployeeBO> empl) throws SQLException {
        AtomicInteger index = new AtomicInteger();
        when(resultSetMock.next())
                .thenAnswer(invocation -> index.get() < empl.size() && index.incrementAndGet() < empl.size());
        when(resultSetMock.previous()).thenAnswer(invocation -> index.get() >= 0 && index.decrementAndGet() >= 0);
        when(resultSetMock.isBeforeFirst()).thenAnswer(invocation -> index.get() == -1);
        when(resultSetMock.first())
                .thenAnswer(invocation -> empl.size() > 0 && index.getAndSet(0) != empl.size());
        when(resultSetMock.isAfterLast()).thenAnswer(invocation -> index.get() == empl.size());
        when(resultSetMock.first()).thenAnswer(
                invocation -> empl.size() > 0 && index.getAndSet(empl.size() - 1) != empl.size());

        when(resultSetMock.getString("FIRST_NAME")).thenAnswer(invocation -> empl.get(index.get()).getFirstName());
        when(resultSetMock.getString("LAST_NAME")).thenAnswer(invocation -> empl.get(index.get()).getLastName());
        when(resultSetMock.getString("ROLE")).thenAnswer(invocation -> empl.get(index.get()).getRole() + "");
        when(resultSetMock.getString("SENIORITY"))
                .thenAnswer(invocation -> empl.get(index.get()).getSeniority() + "");

        index.set(-1);
    }

    private void testSendMail(String expected) throws Exception {
        final ArgumentCaptor<Message> propertiesCaptor = ArgumentCaptor.forClass(Message.class);
        mockStatic(Transport.class);

        EmployeeManager manager = new EmployeeManager();
        manager.sendEmployeesReport(mockConnection);

        verifyStatic(Transport.class);
        Transport.send(propertiesCaptor.capture());

        assertEquals(propertiesCaptor.getValue().getContent(), expected);

        //check caching
        clearInvocations(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);
        manager.sendEmployeesReport(mockConnection);
        assertEquals(propertiesCaptor.getValue().getContent(), expected);
    }

    private void testJsonConvert(String json) throws Exception {
        Employee employee = new Employee();
        String serialized = employee.employeesAsJson(mockConnection);

        JSONAssert.assertEquals(serialized, serialized, json, false);

        clearInvocations(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);
        serialized = employee.employeesAsJson(mockConnection);
        JSONAssert.assertEquals(serialized, serialized, json, false);

    }
}
