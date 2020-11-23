package rs.vegait.timesheet.core.service;

public interface SMTPServer {
    void sendEmail(String toEmail, String subject, String body) throws Exception;
}
