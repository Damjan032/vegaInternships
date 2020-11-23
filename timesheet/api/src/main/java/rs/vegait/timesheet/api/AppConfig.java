package rs.vegait.timesheet.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import rs.vegait.timesheet.core.service.SMTPServer;
import rs.vegait.timesheet.infrastructure.DefaultSMTPServer;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "rs.vegait.timesheet")
public class AppConfig {

    @Value("${datasource.url}")
    private String DB_URL;
    @Value("${datasource.username}")
    private String USER;
    @Value("${datasource.password}")
    private String PASS;
    @Value("${datasource.driver-class-name}")
    private String DATASOURCE_DRIVER;
    @Value("${mail.smtp.host}")
    private String SMTP_HOST;
    @Value("${mail.smtp.socketFactory.port}")
    private String SSL_PORT;
    @Value("${mail.smtp.socketFactory.class}")
    private String SSL_FACTORY_CLASS;
    @Value("${mail.smtp.auth}")
    private String SMTP_AUTH;
    @Value("${mail.address}")
    private String MAIL_ADDRESS;
    @Value("${mail.password}")
    private String MAIL_PASSWORD;

    @Bean(name = "namedFile")
    public Connection creatDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DATASOURCE_DRIVER);
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    @Bean
    public SMTPServer createSMTPServer() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST); //SMTP Host
        props.put("mail.smtp.socketFactory.port", SSL_PORT); //SSL Port
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY_CLASS); //SSL Factory Class
        props.put("mail.smtp.auth", SMTP_AUTH); //Enabling SMTP Authentication
        props.put("mail.smtp.port", SSL_PORT); //SMTP Port

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_ADDRESS, MAIL_PASSWORD);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        return new DefaultSMTPServer(session);
    }
}
