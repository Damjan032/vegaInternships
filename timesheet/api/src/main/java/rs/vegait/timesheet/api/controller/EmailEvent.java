package rs.vegait.timesheet.api.controller;
import org.springframework.context.ApplicationEvent;


public class EmailEvent extends ApplicationEvent {
    private String subject;
    private String content;
    private String[] address;

    public EmailEvent(Object source, String subject, String content, String[] address) {
        super(source);

        this.subject = subject;
        this.content = content;
        this.address = address;
    }

}
