package rs.vegait.timesheet.core.service;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String body);
}
