package com.epam.cleandesign.srp;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

public final class EmployeeManager {

    private List<EmployeeBO> cache = null;
    private Employee emp = new Employee();

    public void sendEmployeesReport(Connection connection) {
        String to = "abcd@gmail.com";
        String from = "web@gmail.com";
        String host = "localhost";
        String MAIL_SMTP_HOST = "mail.smtp.host";
        String SUBJECT_EMAIL = "Employees report";
        String FORMAT_CONTENT = "text/html; charset=utf-8";


        Properties properties = System.getProperties();
        properties.setProperty(MAIL_SMTP_HOST, host);
        Session session = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(SUBJECT_EMAIL);

            String employeesAsHtml = emp.getAllEmployeesAsHtml(connection);

            message.setContent(employeesAsHtml, FORMAT_CONTENT);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }
    }
}
