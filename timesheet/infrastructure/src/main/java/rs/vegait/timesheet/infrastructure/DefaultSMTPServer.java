package rs.vegait.timesheet.infrastructure;


import rs.vegait.timesheet.core.service.SMTPServer;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;


public class DefaultSMTPServer implements SMTPServer {
    private final Session session;

    public DefaultSMTPServer(Session session) {
        this.session = session;
    }

    public void sendEmail(String toEmail, String subject, String body) throws Exception { //TODO Properties param
        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress("no-replay@timesheet.vegait.rs", "NoReply-JD"));
        msg.setReplyTo(InternetAddress.parse("no-replay@timesheet.vegait.rs", false));
        msg.setSubject(subject, "UTF-8");
        msg.setText(body, "UTF-8");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

        Transport.send(msg);
    }
}