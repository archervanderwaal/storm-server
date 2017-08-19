package me.stormma.mail;

import me.stormma.config.MailConfig;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * @description mail util
 * @author stormma
 * @date 2017/8/18
 */
public class MailUtils {

    /**
     * @description create session
     * @return
     */
    public static Session createSession() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.port", "25");
        properties.setProperty("mail.host", MailConfig.EMAIL_HOST);
        properties.setProperty("mail.smtp.auth", "true");
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailConfig.EMAIL_FROM_ADDRESS, MailConfig.EMAIL_PASSWORD);
            }
        };
        return Session.getInstance(properties, authenticator);
    }
}
