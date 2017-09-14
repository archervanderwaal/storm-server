package me.stormma.mail;

import me.stormma.constant.StormApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author stormma
 * @description 邮件服务
 * @date 2017/8/18
 */
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    /**
     * @return
     * @description send message
     */
    public static void sendMessage(String subject, String content) throws MessagingException {
        /*Session session = MailUtils.createSession();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MailConfig.EMAIL_FROM_ADDRESS));
        message.addRecipients(Message.RecipientType.TO, MailConfig.EMAIL_TO_ADDRESS);
        message.setSubject(subject);
        MimeMultipart parts = new MimeMultipart();
        MimeBodyPart part = new MimeBodyPart();
        part.setContent(content, StormApplicationConstant.TEXT_HTML_TYPE);
        parts.addBodyPart(part);
        message.setContent(parts);
        logger.info("======>{}", MailConfig.EMAIL_TO_ADDRESS);
        logger.info("======>{}", MailConfig.EMAIL_FROM_ADDRESS);
        Transport.send(message);*/
    }
}
